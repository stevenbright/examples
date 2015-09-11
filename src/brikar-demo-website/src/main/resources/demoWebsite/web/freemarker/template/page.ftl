<#macro page title>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8" />

    <meta name="Author" content="Alex" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <title>Website Demo &raquo; ${title}</title>

    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.css" />
    <link rel="stylesheet" type="text/css" href="/assets/app/css/global.css" />
  </head>
  <body>

  <#-- Navigation -->
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
      <#-- Brand and toggle get grouped for better mobile display -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#g-app-navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/g/index">Brikar-Demo</a>
      </div>
      <div class="collapse navbar-collapse" id="g-app-navbar-collapse">
        <ul class="nav navbar-nav">
          <li><a href="/g/articles">Articles</a></li>
          <li class="nav-divider"></li>
          <li><a href="/g/about">About</a></li>
          <li class="nav-divider"></li>
          <li><a href="/g/logout">Logout</a></li>
        </ul>
      </div> <#-- /.navbar-collapse -->
    </div> <#-- /.container -->
  </nav>

  <div id="main-content" class="container">
    <#nested/>
  </div>

  <div id="footer">
  </div>

  <#-- Vendor Scripts -->
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.js"></script>
  <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
  <#-- Custom Page Scripts -->
  <script type="text/javascript" src="/assets/app/js/app.js"></script>
</body>
</html>

</#macro>