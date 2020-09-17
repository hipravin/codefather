$(document).ready(function () {
    setInterval(moved1, 50);

});

var d1x = 10;

function moved1() {
    $("#rect1").attr("x", d1x);
    d1x++;
}
