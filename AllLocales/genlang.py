tmpl = '''<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="english">%s</string>
    <string name="native">%s</string>
    <string name="app_name">Language Tester</string>
%s</resources>
'''
phrase_tmpl = '''    <string name="phrase">%s</string>
'''

import sys
import os
import os.path
import shutil
from xml.etree.ElementTree import ElementTree
from Mozilla.Parser import getParser

try:
    cldr_main, en_src, l10n_src, dest = sys.argv[1:5]
except:
    sys.stderr.write("Call with arguments: cldr_main, en_src, l10n_src, dest\n")
    sys.exit(1)

def getCLDRNames(lang):
    tree = ElementTree()
    tree.parse(os.path.join(cldr_main, lang + ".xml"))
    rv = {}
    for e in tree.findall('localeDisplayNames/languages/language'):
        rv[e.attrib['type']] = e.text
    return rv

languageNames = os.path.join(en_src, 'toolkit', 'locales', 'en-US',
                             'chrome', 'global', 'languageNames.properties')
p = getParser(languageNames)
p.readFile(languageNames)
l, m = p.parse()
en_names = getCLDRNames("en")
en_names.update(dict((k, l[v].val) for k, v in m.iteritems()))

all_locales = filter(lambda l:l not in ('x-testing', 'ja-JP-mac'),
                     os.listdir(l10n_src))
for loc in all_locales:
    lang = loc.split('-')[0]
    print loc
    en_name = en_names[lang]
    languageNames = os.path.join(l10n_src, loc,
                                 'toolkit', 'chrome', 'global',
                                 'languageNames.properties')
    p = getParser(languageNames)
    p.readFile(languageNames)
    l, m = p.parse()
    try:
        native = l[m[lang]].val
    except KeyError:
        try:
            native = getCLDRNames(lang)[lang]
        except:
            native = en_name

    browser = os.path.join(l10n_src, loc,
                           'browser', 'chrome', 'browser', 'browser.dtd')
    p = getParser(browser)
    p.readFile(browser)
    l, m = p.parse()
    try:
        phrase = l[m['bookmarkAllTabs.label']].val
        phrase = phrase_tmpl % (phrase.replace("'", "\\'"))
    except:
        print 'no phrase for ' + loc
        phrase = ''
    target = os.path.join(dest, 'values-' + loc.replace('-', '-r'))
    print target
    if not os.path.isdir(target):
        os.makedirs(target)
    (open(os.path.join(target, 'strings.xml'),'w')
     .write((tmpl % (en_name, native, phrase)).encode('utf-8')))

target = os.path.join(dest, 'values')
if not os.path.isdir(target):
    os.makedirs(target)
(open(os.path.join(target, 'locales.xml'), 'w').
 write('''<?xml version="1.0" encoding="utf-8"?>
<resources>
  <string-array name="locales">
''' + '\n'.join('    <string>%s</string>' % l for l in all_locales)
 + '''
  </string-array>
</resources>
'''))
