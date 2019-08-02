$(document).ready(function() 
{
	
	$("#nivel").change(function(request, response) {
			$.ajax({
				url : "/asunto/subsistemas/" + $("#nivel").val(),
				dataType : "json",
				data : {
					term : $("#nivel").val()
				},
				success : function(data) 
				{
					 var html = '<option value="0">--Seleccione Subsistema--</option>';
		                var len = data.length;
		                for ( var i = 0; i < len; i++) {
		                    html += '<option value="' + data[i].id + '">'
		                            + data[i].descripcion + '</option>';
		                }
		                html += '</option>';
		                console.log($("#sub_div"));
		                $('#sub_div').show().delay(2000).show("fast", "easing");
		                
		                $('#subsistema').html(html);
				},
				error: function() {
			        console.log("No se ha podido obtener la información");
			    }
			});
		});

	});
var i = 1;

function addRow()
{
	console.log($("#row"));
	$("#row").clone().find("input").each(function() 
	{
	    $(this).val('').attr('id', function(_, id) { return id + i });
	    $("#row").val('').attr('id', function(_, id) { return id + i });
	}).end().appendTo("#upload_respuesta");
	i++;
}

function fnExcelReport()
{
    var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
    var textRange; var j=0;
    tab = document.getElementById('reporte'); // id of table

    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,"Say Thanks to Sumit.xls");
    }  
    else                 //other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel;charset=UTF-8,%EF%BB%BF' + encodeURIComponent(tab_text));  

    return (sa);
}