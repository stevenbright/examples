<#import "../template/page.ftl" as pt/>
<#import "../template/heading.ftl" as h/>

<@pt.page title="Articles">

<@h.heading/>
<hr/>

<#list articles as article>
  <div class="container">
    <h2>${article.heading}</h2>
    <p>${article.content}</p>
  </div>
</#list>

</@pt.page>