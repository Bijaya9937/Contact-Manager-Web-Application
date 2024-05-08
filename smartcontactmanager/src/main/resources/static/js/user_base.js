
const sidebar = document.querySelector(".sidebar");
const openbtnDiv = document.querySelector(".openmenubtnDiv");
const closebtn = document.getElementById("closeBtn");
const openbtn = document.getElementById("openBtn");
const content = document.querySelector(".content");

closebtn.addEventListener("click", () => {
	sidebar.style.left = "-250px";
	openbtnDiv.style.visibility = "visible"

})

openbtn.addEventListener("click", () => {
	sidebar.style.left = "0";
	openbtnDiv.style.visibility = "hidden";
	content.style.marginRight = "20px";

})


function deleteFunc(cid) {
	swal({
		title: "Are you sure?",
		text: "You want to delete contact",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location="/user/delete/"+cid;
			} else {
				swal("Your contact is safe");
			}
		});
}


var text = document.getElementById('text');
        var newDom = '';
        var animationDelay = 6;

        for(let i = 0; i < text.innerText.length; i++)
        {
            newDom += '<span class="char">' + (text.innerText[i] == ' ' ? '&nbsp;' : text.innerText[i])+ '</span>';
        }

        text.innerHTML = newDom;
        var length = text.children.length;

        for(let i = 0; i < length; i++)
        {
            text.children[i].style['animation-delay'] = animationDelay * i + 'ms';
        }




$(document).ready(function() {
    $('#form1').submit(function(event) {
        event.preventDefault(); // Prevent the default form submission

        // You can add any additional logic or validation for form 1 here

        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function(response) {
                // Handle the success response if needed

                // Now, submit form 2
                $('#form2').submit();
            }
        });
    });
});


function search(){
	let val= $("#search-input").val();

	if(val == '')
	{
		$(".search-result").hide();
		
	}
	else{
		
		//Sending request to the server
		
		let url=`http://localhost:8181/search/${val}`
		
		fetch(url).then((response)=>{
			return response.json();
		}).then((data)=>{
			console.log(data);
			
			let text=`<div class='list-group'>`;
			
			data.forEach((contact) => {
				text+= `<a th:href='@{/contact/${contact.cid}}' id='searchImg' class='list-group-item list-group-action'>${contact.name}</a>`
				console.log(contact.cid);
			})
			text+=`</div>`;
			
			
			$(".search-result").html(text);
			$(".search-result").show();
		});

		
	}
}
