import os, errno
from flask import Flask, render_template, send_from_directory, request, redirect
from MakeWrongNote import process_pdf


app = Flask(__name__)
# app.url_map.strict_slashes = False

cart = {}

@app.route('/')
def index():
    return render_template('index.html')


@app.route('/static/<path:path>')
def static_access(path):
    return send_from_directory('static', path)


@app.route('/<type>')
def list_kmc(type):
    print("list_{}".format(type))
    dirs = [dir for dir in os.listdir(type) if os.path.isdir(os.path.join(type, dir))]
    dirs.sort()
    return render_template('text_list.html', type=type, values=dirs)


@app.route('/<type>/<grade>')
def list_kmc_grade(type, grade):
    print("list_{}_grade".format(type))
    dirs = [dir for dir in os.listdir("{}/{}".format(type, grade)) if os.path.isdir(os.path.join("{}/{}".format(type, grade), dir))]
    dirs.sort()
    return render_template('grade_list.html', type=type, title="", grade=grade, values=dirs)


@app.route('/<type>/<grade>/<sequence>')
def list_kmc_grade_sequence(type, grade, sequence):
    print("list_kmc_grade_sequence")
    files = [dir for dir in os.listdir("{}/{}/{}".format(type, grade, sequence)) if os.path.isfile(os.path.join("{}/{}/{}".format(type, grade, sequence), dir))]
    files.sort()
    print(files)
    return render_template('image_list_checkbox.html', type=type, title="", values=files, grade=grade, sequence=sequence)


@app.route('/<type>/<grade>/<sequence>/<path:path>')
def list_kmc_grade_sequence_question(type, grade, sequence, path):
    print("list_kmc_grade_sequence_question")
    return send_from_directory('{}/{}/{}'.format(type, grade, sequence), path)


@app.route('/<type>/image_list', methods=['GET', 'POST'])
def image_list(type):
    if request.method == 'POST':
        result = request.form.getlist('question')
        print('###################')
        print(result)
        print('###################')
        return render_template('image_list.html', type=type, values=result)
    return ''


@app.route('/<type>/delete_image', methods=['GET', 'POST'])
def delete_image(type):
    if request.method == 'POST':
        result = request.form.getlist('question')
        print(result)
        grade = request.form['grade']
        sequence = request.form['sequence']
        for file in result:
            abs = os.path.abspath(".")
            filepath = abs + file
            print(filepath)
            os.remove(filepath)
    return redirect('/{}/{}/{}'.format(type, grade, sequence))


@app.route('/<type>/file_upload', methods=['GET', 'POST'])
def file_upload(type):
    if request.method == 'POST':
        f = request.files['file']
        f.save(f.filename)
        grade = request.form['grade']
        sequence = request.form['sequence']

        print((f, grade, sequence))

        process_pdf(f.filename, os.path.join("./{}".format(type), grade, sequence), 1 if type == "kmc" else 2)

        os.remove(f.filename)

        return redirect('/{}/{}'.format(type, grade))


@app.route('/<type>/add_cart', methods=['GET', 'POST'])
def add_cart(type):
    if request.method == 'POST':
        result = request.form.getlist('question')
        print(result)
        grade = request.form['grade']
        sequence = request.form['sequence']
        for file in result:
            if type not in cart:
                cart[type] = []
            cart[type].append(file)
    return redirect('/{}/view_cart'.format(type))


@app.route('/<type>/view_cart')
def view_cart(type):
    if type not in cart:
        cart[type] = []

    items = cart[type]
    print(items)

    return render_template('cart_list_checkbox.html', type=type, values=items)


@app.route('/<type>/delete_item', methods=['GET', 'POST'])
def delete_item(type):
    if request.method == 'POST':
        result = request.form.getlist('question')
        print(result)
        for file in result:
            cart[type].remove(file)
    return redirect('/{}/view_cart'.format(type))


@app.route('/<type>/delete_cart', methods=['GET', 'POST'])
def delete_cart(type):
    if request.method == 'POST':
        cart[type] = []
        grade = request.form['grade']

    return redirect('/{}/{}'.format(type, grade))

# list_kmc_sequence('E3')

