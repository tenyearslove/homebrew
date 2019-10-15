import os, errno
from flask import Flask, render_template, send_from_directory, request, redirect
from MakeWrongNote import process_pdf


app = Flask(__name__)


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/static/<path:path>')
def static_access(path):
    return send_from_directory('static', path)


@app.route('/kmc')
def list_kmc():
    print("list_kmc")
    dirs = [dir for dir in os.listdir("kmc") if os.path.isdir(os.path.join("kmc", dir))]
    dirs.sort()
    return render_template('text_list.html', values=dirs)


@app.route('/kmc/<grade>')
def list_kmc_grade(grade):
    print("list_kmc_grade")
    dirs = [dir for dir in os.listdir("kmc/{}".format(grade)) if os.path.isdir(os.path.join("kmc/{}".format(grade), dir))]
    dirs.sort()
    return render_template('grade_list.html', title="", grade=grade, values=dirs)


@app.route('/kmc/<grade>/<sequence>')
def list_kmc_grade_sequence(grade, sequence):
    print("list_kmc_grade_sequence")
    files = [dir for dir in os.listdir("kmc/{}/{}".format(grade, sequence)) if os.path.isfile(os.path.join("kmc/{}/{}".format(grade, sequence), dir))]
    files.sort()
    print(files)
    return render_template('image_list_checkbox.html', title="", values=files, grade=grade, sequence=sequence)


@app.route('/kmc/<grade>/<sequence>/<path:path>')
def list_kmc_grade_sequence_question(grade, sequence, path):
    print("list_kmc_grade_sequence_question")
    return send_from_directory('kmc/{}/{}'.format(grade, sequence), path)


@app.route('/kmc/image_list', methods=['GET', 'POST'])
def image_list():
    if request.method == 'POST':
        result = request.form.getlist('question')
        print('###################')
        print(result)
        print('###################')
        return render_template('image_list.html', values=result)
    return ''


@app.route('/kmc/delete_image', methods=['GET', 'POST'])
def delete_image():
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
    return redirect('/kmc/{}/{}'.format(grade, sequence))


@app.route('/kmc/file_upload', methods=['GET', 'POST'])
def file_upload():
    if request.method == 'POST':
        f = request.files['file']
        f.save(f.filename)
        grade = request.form['grade']
        sequence = request.form['sequence']

        print((f, grade, sequence))

        process_pdf(f.filename, os.path.join("./kmc", grade, sequence))

        os.remove(f.filename)

        return redirect('/kmc/{}'.format(grade))


# list_kmc_sequence('E3')

