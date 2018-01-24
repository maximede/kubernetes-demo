var express = require('express')
var app = express();

app.set('port', (process.env.PORT || 8080))
app.use(express.static(__dirname + '/public'))

app.get('/', function(request, response) {
  const my_var = process.env.MY_VAR;
  const my_secret = process.env.MY_SECRET_VAR;
  const pod_name = process.env.POD_NAME;

  response.send(
  `
<html>
<body>
Hello Node !<br/>
Pod name is ${pod_name}<br/>
Env Variable is : ${my_var}<br/>
Env secret Variable is : ${my_secret}<br/>
</body>
</html>`
  );
})

app.listen(app.get('port'), function() {
  console.log("Node app is running at localhost:" + app.get('port'))
})