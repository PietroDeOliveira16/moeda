var series;
var root;
var chart;
var easing;
var legend;
var cursor;
var xAxis;
var yAxis;
var data;

function genChart(divHtml, dadosJson){
    // Assigning root div
    root = am5.Root.new(divHtml);

    root.setThemes([
      am5themes_Animated.new(root)
    ]);

    // Creating the chart
    chart = root.container.children.push(am5xy.XYChart.new(root, {
      focusable: true,
      panX: true,
      panY: true,
      wheelX: "panX",
      wheelY: "zoomX",
      pinchZoomX:true,
      paddingLeft: 0
    }));

    chart.get("colors").set("step", 5);

    easing = am5.ease.linear;

    legend = chart.plotContainer.children.push(am5.Legend.new(root, {
      centerY: am5.p100,
      y: am5.p100
    }));

    legend.valueLabels.template.set("width", 120);

    // Creating cursor at the chart
    cursor = chart.set("cursor", am5xy.XYCursor.new(root, {
      behavior: "none"
    }));
    cursor.lineY.set("visible", false);

    // Create axes
    xAxis = chart.xAxes.push(am5xy.DateAxis.new(root, {
      maxDeviation: 0.5,
      groupData: false,
      extraMax:0.1, // this adds some space in front
      extraMin:-0.1,  // this removes some space form th beginning so that the line would not be cut off
      baseInterval: {
        timeUnit: "minute",
        count: 1
      },
      renderer: am5xy.AxisRendererX.new(root, {
        minorGridEnabled: true,
        minGridDistance: 50
      }),
      tooltip: am5.Tooltip.new(root, {})
    }));

    yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
      renderer: am5xy.AxisRendererY.new(root, {
        pan:"zoom"
      })
    }));

    series = chart.series.push(am5xy.LineSeries.new(root, {
      name: "Series 1",
      xAxis: xAxis,
      yAxis: yAxis,
      valueYField: "valor",
      valueXField: "data",
      tooltip: am5.Tooltip.new(root, {
        pointerOrientation: "horizontal",
        labelText: "{valueY}"
      })
    }));

    series.bullets.push(function(root, series, dataItem) {
      // only create sprite if bullet == true in data context
      if (dataItem.dataContext.bullet) {
        var container = am5.Container.new(root, {});
        var circle0 = container.children.push(am5.Circle.new(root, {
          radius: 5,
          fill: am5.color(0xff0000)
        }));
        var circle1 = container.children.push(am5.Circle.new(root, {
          radius: 5,
          fill: am5.color(0xff0000)
        }));

        circle1.animate({
          key: "radius",
          to: 20,
          duration: 1000,
          easing: am5.ease.out(am5.ease.cubic),
          loops: Infinity
        });
        circle1.animate({
          key: "opacity",
          to: 0,
          from: 1,
          duration: 1000,
          easing: am5.ease.out(am5.ease.cubic),
          loops: Infinity
        });

        return am5.Bullet.new(root, {
          locationX:undefined,
          sprite: container
        })
      }
    })

    // Add scrollbar
    chart.set("scrollbarX", am5.Scrollbar.new(root, {
      orientation: "horizontal"
    }));

      // Set data
    data = dadosJson;
    data[data.length - 1].bullet = true;
    series.data.setAll(data);


    // Make stuff animate on load
    series.appear(1000);
    chart.appear(1000, 100);
}

function generateChartData(divHtml, moeda) {
    $.ajax({
        url: '/getChartData',
        method: 'POST',
        data: {
            moeda: moeda
        },
        success: function(dataServer){
            genChart(divHtml, dataServer);
        },
        error: function(){
            alert("Ih carai");
        }
    });
}

function generateNextValue(moeda){
    $.ajax({
    url: '/getNovaCotacao',
    method: 'POST',
    data: {
        moeda: moeda
    },
    success: function(dataServer){
        if(series.dataItems[series.dataItems.length - 1].dataContext.data != dataServer.data){
            addData(dataServer);
        }
    },
    error: function(){}
    });
}

function addData(dataServer) {
if (typeof series !== 'undefined') {
  var lastDataItem = series.dataItems[series.dataItems.length - 1];
  var lastValue = lastDataItem.get("valueY");
  var lastDate = new Date(lastDataItem.get("valueX"));
  series.data.push(dataServer);

  var newDataItem = series.dataItems[series.dataItems.length - 1];
  newDataItem.animate({
    key: "valueYWorking",
    to: newDataItem.get("valueY"),
    from: lastValue,
    duration: 600,
    easing: easing
  });

  // use the bullet of last data item so that a new sprite is not created
  newDataItem.bullets = [];
  newDataItem.bullets[0] = lastDataItem.bullets[0];
  newDataItem.bullets[0].get("sprite").dataItem = newDataItem;
  // reset bullets
  lastDataItem.dataContext.bullet = false;
  lastDataItem.bullets = [];


  var animation = newDataItem.animate({
    key: "locationX",
    to: 0.5,
    from: -0.5,
    duration: 600
  });
  if (animation) {
    var tooltip = xAxis.get("tooltip");
    if (tooltip && !tooltip.isHidden()) {
      animation.events.on("stopped", function () {
        xAxis.updateTooltip();
      })
    }
  }
  }
}

generateChartData("chartBtc", "BTC");

setInterval(function () {
  generateNextValue("BTC");
}, 30000)
