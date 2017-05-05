var urlChartMonth,
chartMonthContainer,
urlChartYear,
urlChartGlobal,
chartGlobalContainer,
chartYearContainer,
loadingMessage,
chartMonth,
chartYear,
chartGlobal
;

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
    
    $('[data-toggle="tab"]').on('shown.bs.tab', function () {
        if(chartMonth){
    		chartMonth.reflow();
        }
        if(chartYear){
        	chartYear.reflow();
        }
        if(chartGlobal){
        	chartGlobal.reflow();
        }
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

/* ********************************************* */
/* CHART MONTH MEETINGS ATTENDANCE */
function initChartMonth(parameters){
	urlChartMonth = parameters.url;
	chartMonthContainer = parameters.container;
	updateChartMonth();
}

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

/* ********************************************* */
//CHART YEAR AVERAGE ATTENDANCE
function initChartYear(parameters){
	urlChartYear = parameters.url;
	chartYearContainer = parameters.container;
	
	updateChartYear();
}

function updateChartYear(){
	var url = urlChartYear;
	
	if($('#' + chartYearContainer).length){
        $.getJSON(url, function (config) {
            options = config;
            options.chart.renderTo = chartYearContainer;
            options.series[0].fillColor = {
                linearGradient: {
                    x1: 0,
                    y1: 0,
                    x2: 0,
                    y2: 1
                },
                stops: [
                    [0, 'rgba(120, 253, 154, .2)'],
                    [1, 'rgba(120, 253, 154, 0)']
                ]
            };
            if (options.series[1]) {
                options.series[1].fillColor = {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, 'rgba(243, 132, 252, .1)'],
                        [1, 'rgba(243, 132, 252, 0)']
                    ]
                };
            }
            if (options.series[2]) {
                options.series[2].fillColor = {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, 'rgba(107, 193, 253, .1)'],
                        [1, 'rgba(107, 193, 253, 0)']
                    ]
                };
            }
            if (options.series[3]) {
                options.series[3].fillColor = {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, 'rgba(168, 32, 132, .1)'],
                        [1, 'rgba(168, 32, 132, 0)']
                    ]
                };
            }
            if (options.series[4]) {
                options.series[4].fillColor = {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, 'rgba(104, 254, 224, .1)'],
                        [1, 'rgba(104, 254, 224, 0)']
                    ]
                };
            }
            chartYear = new Highcharts.Chart(options);
        });
    }
}

/* ********************************************* */
//CHART ALL ATTENDANCES
function initChartGlobal(parameters){
	urlChartGlobal = parameters.url;
	chartGlobalContainer = parameters.container;
	updateChartGlobal();
}

function updateChartGlobal(){
	var url = urlChartGlobal;
	if($('#' + chartGlobalContainer).length){
        $.getJSON(url, function (config) {
            options = config;
            options.chart.renderTo = chartGlobalContainer;
            options.tooltip = {
                formatter: function () {
                    return Highcharts.dateFormat('%b %Y', new Date(this.x)) + ': ' + this.y;
                }
            };
            chartGlobal = new Highcharts.Chart(options);
        });
	}
}