$(function () {
    var vm = new Vue({
        el: '#appointmentList',
        data: {
            appointments: [],
            pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 3},
            pageNums: []
        },
        created: function () {
            var queryParas = {bookDate: $('#bookDate').val(), state: $('#state').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

            $.ajax({
                type: "get",
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                dataType:"json",
                url: "http://localhost:8080/admin/appointment-list",
                data: queryParas,
                success: function (res) {
                    vm.appointments = res.data;

                    setPagination(res.count);

                }
            });
        },
        methods: {
            queryCurrentPage: function(event){
                this.pagination.currentPage = parseInt(event.target.text);

                var queryParas = {bookDate: $('#bookDate').val(), state: $('#state').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType:"json",
                    url: "http://localhost:8080/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;

                        setPagination(res.count);
                    }
                });
            },
            previousPage: function () {
                vm.pagination.currentPage -= 1;

                if(vm.pagination.currentPage < vm.pagination.startPage){
                    vm.pagination.endPage = vm.pagination.currentPage;
                    vm.pagination.startPage = vm.pagination.endPage - vm.pagination.pageSize + 1;

                    setPageNums();
                }

                var queryParas = {bookDate: $('#bookDate').val(), state: $('#state').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "http://localhost:8080/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;
                    }
                });
            },
            nextPage: function () {
                vm.pagination.currentPage += 1;

                if(vm.pagination.currentPage > vm.pagination.endPage){
                    vm.pagination.startPage = vm.pagination.currentPage;

                    if(vm.pagination.totalPage - vm.pagination.currentPage + 1 >= vm.pagination.pageSize){
                        vm.pagination.endPage = vm.pagination.currentPage + vm.pagination.pageSize -1;
                    }else{
                        vm.pagination.endPage = vm.pagination.totalPage;
                    }

                    setPageNums();
                }

                var queryParas = {bookDate: $('#bookDate').val(), state: $('#state').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "http://localhost:8080/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;
                    }
                });

            }
        }
    });


    function setPagination(recordCount) {

        var totalPage = 0;

        if(recordCount % vm.pagination.size == 0){
            totalPage = recordCount / vm.pagination.size;
        }else{
            totalPage = parseInt(recordCount / vm.pagination.size) + 1;
        }

        vm.pagination.totalPage = totalPage;

        vm.pagination.startPage = 1;

        if(totalPage > vm.pagination.pageSize){
            vm.pagination.endPage = vm.pagination.pageSize;
        }else{
            vm.pagination.endPage = totalPage;
        }

        setPageNums();
    }
    
    function setPageNums() {
        vm.pageNums = [];
        for(var i = 0; i < (vm.pagination.endPage - vm.pagination.startPage + 1); i++){
            vm.pageNums.push(i + vm.pagination.startPage);
        }
    }

});


