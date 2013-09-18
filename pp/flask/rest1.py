import json
from flask import Flask, request, Response

app = Flask(__name__)

@app.route("/")
def index():
    return "Index Page"

@app.route("/rest/version")
def version():
    return Response(response = '{ "version": "1.0.0" }', status = 200, mimetype = "application/json" )

# Test this with curl:
# curl -H 'Content-Type: application/json' -X POST -d '{"id": 134}' http://127.0.0.1:5000/rest/task/submit
@app.route("/rest/task/submit", methods = ['POST'])
def submit_task():
    print 'data = %s' % request.data
    task = json.loads(request.data)
    task_id = task['id']
    return Response(response = '{ "taskId": %s, "msg": 1 }' % task_id, status = 200, mimetype = "application/json")

if __name__ == "__main__":
    app.run()

