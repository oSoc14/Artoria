import urllib.request
import urllib.parse
import re

def translate(text, source='en', destination='nl'):
    link = 'http://translate.google.com/translate_a/'
    escaped_text = urllib.parse.quote(text, encoding='utf-8')

    url = "{}t?client=t&text={}&sl={}&tl={}".format(link,
                                                    escaped_text,
                                                    source,
                                                    destination)
    user_agent = ' Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0)'
    user_agent += ' Gecko/20100101 Firefox/12.0'
    request = urllib.request.Request(url=url,
                                     data=b'None',
                                     headers={'User-Agent': user_agent})
    handler = urllib.request.urlopen(request)
    response = handler.read().decode('utf-8')

    response = response[response.find('"') + 1:]
    response = response[:response.find('"')]

    return response

def get_list_to_translate():
    words_to_translate = []
    strings_file = open("strings.xml", mode='r', encoding='utf-8')
    lines = strings_file.read().split('\n')
    strings_file.close()

    item_pattern = re.compile('(item)>(.*)<')
    string_pattern = re.compile('name="(.*)">(.*)<')

    for line in lines:
        for pattern in [string_pattern, item_pattern]:
            match = pattern.search(line)
            if match:                
                words_to_translate.append([match.group(1), match.group(2)])
                break
        else:
            words_to_translate.append([None, line])

    return words_to_translate

def translate_and_write_file(tuples_to_translate, lang):
    indent = '    '
    item_format = '{space}<item>{tr}</item>\n'
    string_format = '{space}<string name="{name}">{tr}</string>\n'
    out_file = open("strings" + lang + ".xml", mode='w', encoding='utf-8')
    for name, val in tuples_to_translate:
        if not name:
            out_file.write(val + '\n')
            continue
        translation = translate(val.replace("'",""),
                                source='en',
                                destination=lang)
        if name == 'item':
            out_string = item_format.format(space=indent*2,
                                            tr=translation)
        else:
            out_string = string_format.format(space=indent,
                                              name=name,
                                              tr=translation)
        out_file.write(out_string.replace("'","\\'"))
    out_file.close()

if __name__ == "__main__":
    TUPLES = get_list_to_translate()
    for code in ["it", "es", "de", "fr", "ru"]:
        translate_and_write_file(TUPLES, code)
