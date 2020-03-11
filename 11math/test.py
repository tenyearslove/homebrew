import pdfkit

config = pdfkit.configuration(wkhtmltopdf='/usr/bin/wkhtmltopdf')
options = {
    'proxy': '168.219.61.252:8080'
}
pdfkit.from_url('https://www.11math.com/calc#2A029132', 'out.pdf', configuration=config, options=options)