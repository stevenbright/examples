
$(document).ready(function () {
  console.log('Index page has been loaded');
});

$('#j-main-clickme').click(function () {
  console.log('Clickme button has been clicked at', new Date());
  var sign = $('<p>Clicked!</p>')
  sign.addClass(getRandomBgClass());
  $('#j-button-fadeouts').append(sign);
  sign.fadeOut('slow');
});

function getRandomBgClass() {
  var i = Math.floor(Math.random() * 4);
  switch (i) {
    case 0:
      return 'bg-success';
    case 1:
      return 'bg-info';
    case 2:
      return 'bg-warning';
    case 3:
      return 'bg-danger';
    default:
      return 'bg-primary';
  }
}
