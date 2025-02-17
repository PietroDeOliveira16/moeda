function genChart(divHtml, dadosJson){
    // Assigning root div
    var root = am5.Root.new(divHtml);

    root.setThemes([
      am5themes_Animated.new(root)
    ]);

    // Creating the chart
    var chart = root.container.children.push(am5xy.XYChart.new(root, {
      panX: true,
      panY: true,
      wheelX: "panX",
      wheelY: "zoomX",
      pinchZoomX:true,
      paddingLeft: 0
    }));

    chart.get("colors").set("step", 5);

    var legend = chart.plotContainer.children.push(am5.Legend.new(root, {
      centerY: am5.p100,
      y: am5.p100
    }));

    legend.valueLabels.template.set("width", 120);

    // Creating cursor at the chart
    var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {
      behavior: "none"
    }));
    cursor.lineY.set("visible", false);

    // Create axes
    var xAxis = chart.xAxes.push(am5xy.DateAxis.new(root, {
      maxDeviation: 0.2,
      baseInterval: {
        timeUnit: "minute",
        count: 1
      },
      renderer: am5xy.AxisRendererX.new(root, {
        minorGridEnabled:true
      }),
      tooltip: am5.Tooltip.new(root, {})
    }));

    var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
      renderer: am5xy.AxisRendererY.new(root, {
        pan:"zoom"
      })
    }));

    var series = chart.series.push(am5xy.LineSeries.new(root, {
      name: "Series",
      xAxis: xAxis,
      yAxis: yAxis,
      valueYField: "valor",
      valueXField: "data",
      tooltip: am5.Tooltip.new(root, {
        labelText: "{valueY}"
      })
    }));

    // Add scrollbar
    chart.set("scrollbarX", am5.Scrollbar.new(root, {
      orientation: "horizontal"
    }));

      // Set data
    var data = dadosJson;
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
        success: function(data){
            genChart(divHtml, data);
        },
        error: function(){
            alert("Ih carai");
        }
    });
}

generateChartData("chartDolar", "USD");
