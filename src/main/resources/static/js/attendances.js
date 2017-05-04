var urlChartMonth,
chartMonthContainer,
loadingMessage;

$(document).ready(function(){
	$('#box-memorial input[type="hidden"]').prependTo('#box-memorial');
    $('#box-special-week input[type="hidden"]').prependTo('#box-special-week');
    
    $(document).on('click', '.changePeriod', function(event){
    	event.preventDefault();
    	$('#tab_current_month').loading({
    		theme: 'dark',
    		message: loadingMessage
    	});
    	
    	var url = $(this).attr('href');
    	$.get($(this).attr('href'), function(data){
    		$('#tab_current_month_content').html(data);
    		period = getParameterByName('period', url);
    		updateChartMonth(period);
    	});
    });
});

function initLoading(config){
	loadingMessage = config.message;
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function initTooltip(){
	$('[data-toggle="tooltip"]').tooltip();
}

function initConfirmation(messages){
	$('[data-toggle="confirmation"]').confirmation({
        title: messages.title,
        placement: 'left',
        rootSelector: '[data-toggle="confirmation"]',
        singleton: true,
        popout: true,
        btnCancelClass: 'btn-xs btn-danger',
        btnOkLabel: messages.btnOkLabel,
        btnCancelLabel: messages.btnCancelLabel
    });
}

function initDatePicker(messages){
	$('.date-picker').datetimepicker({
        locale: messages.locale,
        format: messages.format,
        showClear: true,
        maxDate: moment(),
        icons: {
            time: "fa fa-clock-o",
            date: "fa fa-calendar",
            up: "fa fa-arrow-up",
            down: "fa fa-arrow-down"
        }
    });
}

function initTouchspin(){
	$('.touchspin').TouchSpin({
        min: 0,
        max: 1000,
        step: 1
    });
}

function initChartMonth(parameters){
	urlChartMonth = parameters.url;
	chartMonthContainer = parameters.container;
}

/* CHART MONTH MEETINGS ATTENDANCE */
function updateChartMonth(period){
	var url = urlChartMonth;
	if(typeof period !== 'undefined'){
		url = url + '?period=' + period;
	}
	
	$('#tab_current_month').loading('stop');
    if($('#' + chartMonthContainer).length){
    	$.getJSON(url, function (config) {
            options = config;
            options.chart.renderTo = chartMonthContainer;
            options.tooltip = {
                dateTimeLabelFormats: {
                    day: '%e %B %Y'
                }
            }
            chartMonth = new Highcharts.Chart(options);
        });
    }
}