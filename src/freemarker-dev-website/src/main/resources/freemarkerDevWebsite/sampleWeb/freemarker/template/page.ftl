<#macro common title>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="Author" content="Alex" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<title>Freemarker Dev Website Demo &raquo; ${title}</title>

<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"/>
</head>
<body>

<div id="main-content" class="container">
  <#nested/>
</div>

<#-- Bundle Page Script -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="/js/main.js"></script>

</body>
</html>

</#macro>
