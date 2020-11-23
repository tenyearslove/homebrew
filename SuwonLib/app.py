import suwonlib
from flask import Flask, render_template, request

app = Flask(__name__)


@app.route('/')
@app.route('/index')
def index():
    posts = []
    return render_template('search.html', posts=posts)


@app.route('/search', methods=['GET', 'POST'])
def search():
    if request.method == 'POST':
        keyword = request.form['keyword']
        print(f"kk {keyword}")
        posts = suwonlib.search_by_keyword(keyword)
        print(posts)
        return render_template('search.html', posts=posts, keyword=keyword)


@app.route('/unmanned_rsrv')
def unmanned_rsrv():



@app.route('/about')
def about():
    return render_template('about.html', title='About')


if __name__ == '__main__':
    app.run()
