$(function () {
   var vm = new Vue({
       el: "#user-list",
       data: {
           users: [],
           pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 3},
           pageNums: [],
           searchConditions: {email: "", firstName:"", state:""},
           pageForm: {userId: "", firstName: "", lastName: "", email: ""
               , password:"", phone:"", postcode:"", address:"", role: "", state: ""}
       },
       created: function () {
           this.query(1);
       },
       watch: {
           pageForm: function (newValue, oldValue) {
               validateForm(this.validateFields);
           }
       },
       methods: {
           query: function (page) {
               this.pagination.currentPage = page;

               this.searchConditions.email = $('#emailSearch').val();
               this.searchConditions.firstName = $('#firstNameSearch').val();
               this.searchConditions.state = $('#stateSearch').val();

               var queryParas = {email: this.searchConditions.email, firstName: this.searchConditions.firstName,
                                state: this.searchConditions.state, currentPage: this.pagination.currentPage, size: this.pagination.size};

               $.ajax({
                   type: "get",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   dataType:"json",
                   url: "http://localhost:8080/admin/user-list",
                   data: queryParas,
                   success: function (res) {
                       if(res.code == "0"){
                           vm.users = res.data;
                           vm.pagination.listCount = res.count;
                           setPagination();

                           if(res.count == 0){
                               alert("Relevant information is not available");
                           }
                       }else{
                           alert(res.message);
                       }
                   }
               });
           },
           search: function () {
               this.query(1);
           },
           queryCurrentPage: function(event){
               this.query(parseInt(event.target.text));
           },
           previousPage: function () {
               vm.pagination.currentPage -= 1;

               this.query(vm.pagination.currentPage);

           },
           nextPage: function () {
               vm.pagination.currentPage += 1;

               this.query(vm.pagination.currentPage);
           },
           addItem: function () {
               clearFormElements(this.pageForm);

               this.pageForm.role = "USER";
               this.pageForm.state = "Active";
           },
           editItem: function (event) {
               for(var index in this.users){
                   if(event.target.id == this.users[index].userId){
                       this.pageForm.userId = this.users[index].userId;
                       this.pageForm.firstName = this.users[index].firstName;
                       this.pageForm.lastName = this.users[index].lastName;
                       this.pageForm.email = this.users[index].email;
                       this.pageForm.phone = this.users[index].phone;
                       this.pageForm.postcode = this.users[index].postcode;
                       this.pageForm.address = this.users[index].address;
                       this.pageForm.role = this.users[index].role;
                       this.pageForm.state = this.users[index].state;
                   }
               }
           },
           saveItem: function () {
               if(validateForm(this.validateFields)){
                   return;
               }

               $.ajax({
                   type: "post",
                   dataType:"json",
                   url: "http://localhost:8080/admin/users",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   data: vm.pageForm,
                   success: function (res) {
                       if(res.code == "0"){
                           $('#pageFormModal').modal('hide');
                           vm.query(vm.pagination.currentPage);
                       }else{
                           alert(res.message);
                       }
                   }
               });
           }

       }
   });

    vm.validateFields = {firstName:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The name is required'
                                    }
                                }
                            }
                        , email:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The valid date(From) is required'
                                    }
                                }
                            }
                    };

    function validateForm(validateFields) {
        var ret = false;

        for(var fieldName in validateFields){

            var validators = validateFields[fieldName].validators;

            for(var validatorName in validators){
                if(validatorName == "notEmpty"){
                    if($('#' + fieldName).val() == null || $('#' + fieldName).val() == ""){
                        $('#'+fieldName + ' ~ .error-message').remove();
                        $('#'+fieldName).after("<span class='error-message'>"+ validators[validatorName].message +"</span>");
                        ret = true;
                    }else{
                        $('#'+fieldName + ' ~ .error-message').remove();
                    }
                }
            }


        }

        return ret;
    }

    function setPagination() {

        if(vm.pagination.listCount % vm.pagination.size == 0){
            vm.pagination.totalPage  = vm.pagination.listCount / vm.pagination.size;
        }else{
            vm.pagination.totalPage  = parseInt(vm.pagination.listCount / vm.pagination.size) + 1;
        }

        if(vm.pagination.currentPage > vm.pagination.endPage){
            vm.pagination.startPage = vm.pagination.currentPage;

            if(vm.pagination.totalPage - vm.pagination.currentPage + 1 >= vm.pagination.pageSize){
                vm.pagination.endPage = vm.pagination.currentPage + vm.pagination.pageSize -1;
            }else{
                vm.pagination.endPage = vm.pagination.totalPage;
            }

        }else if(vm.pagination.currentPage < vm.pagination.startPage){
            vm.pagination.endPage = vm.pagination.currentPage;
            vm.pagination.startPage = vm.pagination.endPage - vm.pagination.pageSize + 1;
        }else{
            if(vm.pagination.totalPage > vm.pagination.pageSize){
                vm.pagination.endPage = vm.pagination.pageSize;
            }else{
                vm.pagination.endPage = vm.pagination.totalPage;
            }
        }

        vm.pageNums = [];
        for(var i = 0; i < (vm.pagination.endPage - vm.pagination.startPage + 1); i++){
            vm.pageNums.push(i + vm.pagination.startPage);
        }
    }

    function clearFormElements(form) {
        for(var index in form){
            form[index] = "";
        }
    }
});