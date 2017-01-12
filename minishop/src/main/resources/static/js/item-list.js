$(function(){
    // $("a[name='selectModel']").click(function () {
    //     alert("ok");
    // });
    // $('a[name="itemBrand"]').each(function () {
    //     if($('selectedBrand').val() === $(this).id){
    //         $(this).addClass("active");
    //     }
    // })


    var modelsComponent = Vue.extend({
        el: '#model-row',
        data: {
            items: []
        },
        mounted: function () {
            var vm = this;
            $.ajax({
                url: 'http://localhost:8080/client/itemTypes/1',
                method: 'GET',
                dataType: 'json',
                success: function(result){

                    if(result.code = "0"){
                        vm.items = result.rs;
                    }



                },
                error: function (e) {
                    console.log(e);
                }
            })
        }
    });
    var brandrow = new Vue({
        el: '#brand-row',
        data: {
            items: []
        },
        mounted: function () {
            var vm = this;
            $.ajax({
                url: 'http://localhost:8080/client/itemTypes',
                method: 'GET',
                dataType: 'json',
                success: function(result){

                    if(result.code = "0"){
                        vm.items = result.rs;
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        },
        deactivated: function () {
            alert("d");
        },
        methods: {
            selectModels: function (event) {
                this.items.pop();
                //var dd = this.items[0].itemTypeId;

               //alert(event.target.id);
                //var myComponentInstance = new modelsComponent();

            }
        }
    });





    // var modelrow = new Vue({
    //     el: '#model-row',
    //     data: {
    //         items: [
    //             {itemTypeId:'1',name: 'iPhone' },
    //             {itemTypeId:'2',name: 'iPad' }
    //         ]
    //     }
    // });
})