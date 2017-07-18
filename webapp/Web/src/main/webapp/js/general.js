$(document).ready(function () {
    var interval = setInterval(function () {
        var momentNow = moment();
        $('#now-datetime').html('<i class="fa fa-clock-o"></i>' + momentNow.format('DD MMM YYYY') + ' | '
                .substring(0, 3).toUpperCase() + momentNow.format('hh:mm:ss A'));
    }, 500);

    if (document.location.pathname.indexOf("Welcome") === -1) {
        $("li:has(a[href='" + location.pathname + "'])").addClass('active');
        $("li:has(a[href='" + location.pathname + "'])").children('ul').addClass("in");
    }

});

function numberWithCommas(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    };


function percentFormat(x, lead) {
    return ((x * 100).toFixed(lead)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '%';
    };

function sortFunction(a, b) {
    if (a[0] === b[0]) {
        return 0;
    }
    else {
        return (a[0] < b[0]) ? -1 : 1;
    }
}