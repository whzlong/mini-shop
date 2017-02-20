(function ($) {
    $.fn.appCalendar = function () {
        //$(this).append(generateCalendarTable());
        this.append(generateCalendarTable());
    }

    function generateCalendarTable() {
        // var calendarTable;
        //
        // var calendarHeadr = "<thead><th>SU</th><th>MO</th><th>TU</th><th>WE</th><th>TH</th><th>FR</th><th>SA</th></thead>";
        //
        // var calendarBody = "<tbody>";
        //
        // for (var i = 0; i < 6; i++){
        //     var newRow = document.createElement("tr");
        //     for(var j = 0; j < 7; j++){
        //         var newDate = document.createElement("td");
        //         var date;//获取日期信息
        //         newDate.innerText = date;
        //         newRow.appendChild(newDate);
        //     }
        //     rows.appendChild(newRow);
        // }
        //
        // return rows;

        var calendarTable = $("<table class='app-calendar'></table>");

        var calendarHeader = $("<thead></thead>");
        calendarHeader.append("<th>SU</th>");
        calendarHeader.append("<th>MO</th>");
        calendarHeader.append("<th>TU</th>");
        calendarHeader.append("<th>WE</th>");
        calendarHeader.append("<th>TH</th>");
        calendarHeader.append("<th>FR</th>");
        calendarHeader.append("<th>SA</th>");

        var calendarBody = $("<tbody></tbody>");

        for(var i = 0; i < 6; i++){
            var row = $("<tr></tr>");

            for(var j = 0; j < 7; j++){
                var col = $("<td class='day'>1</td>");

                row.append(col);
            }

            calendarBody.append(row);
        }

        // var bodyRow = $("<tr></tr>");
        // bodyRow.append("<td>1</td>");
        // bodyRow.append("<td>2</td>");
        // bodyRow.append("<td>3</td>");
        // bodyRow.append("<td>4</td>");
        // bodyRow.append("<td>5</td>");
        // bodyRow.append("<td>6</td>");
        // bodyRow.append("<td>7</td>");
        //
        // calendarBody.append(bodyRow);

        calendarTable.append(calendarHeader);
        calendarTable.append(calendarBody);

        return calendarTable;
    }

})(jQuery);