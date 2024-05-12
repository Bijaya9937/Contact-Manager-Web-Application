


/*let navItems=document.getElementById("navItems");
function closeMenu()
{
	navItems.style.right="-200px";
}
function openMenu()
{
	navItems.style.right="0px";
}*/

const menuIcon = document.getElementById('menuIcon');
const navItems = document.getElementById('navItems');

// Function to open the navbar menu
function openMenu() {
    navItems.style.right = '0';
}

// Function to close the navbar menu
function closeMenu() {
    navItems.style.right = '-100%';
}

// Event listener for the menu icon
menuIcon.addEventListener('click', openMenu);

function contactus(){
	console.log("You have clicked ");
	Swal.fire({
  title: 'Contact Us',
  html: `<input type="text" id="name" class="swal2-input" placeholder="Full Name ">
  <input type="email" id="email" class="swal2-input" placeholder="Email Address">
  <input type="text" id="phone" class="swal2-input" placeholder="Phone Number"><br>
  <small> * Don't want to contact ? Click out of the box <small>`,
  confirmButtonText: 'Submit',
  focusConfirm: false,
  preConfirm:  () => {
	const name = Swal.getPopup().querySelector('#name').value
    const email = Swal.getPopup().querySelector('#email').value
    const phone = Swal.getPopup().querySelector('#phone').value
    if (!name || !email || !phone) {
      Swal.showValidationMessage(`Please Fill All Fields`)
    }
    return { name: name, email: email, phone:phone }
  }
}).then((result) => {
  	if (!result.value || !result.value.name) {
      Swal.fire('<p><b>ContactHub</b><br><br>Thank you so much for visiting us<br><br> Most Welcome &#128591 </p>');
    } else {
      Swal.fire('<p><b>ContactHUB</b><br><br>We got your request. Our team will contact you very soon.<br><br> Thank You For Contacting Us <b>&#128591</b> </p>');
    }
})
Swal.getPopup().classList.add('custom-swal-class');
}


var words = ['Connect seamlessly', 'Manage effortlessly', 'Amplify your relationships'],
    part,
    i = 0,
    offset = 0,
    len = words.length,
    forwards = true,
    skip_count = 0,
    skip_delay = 15,
    speed = 70;
var wordflick = function () {
  setInterval(function () {
    if (forwards) {
      if (offset >= words[i].length) {
        ++skip_count;
        if (skip_count == skip_delay) {
          forwards = false;
          skip_count = 0;
        }
      }
    }
    else {
      if (offset == 0) {
        forwards = true;
        i++;
        offset = 0;
        if (i >= len) {
          i = 0;
        }
      }
    }
    part = words[i].substr(0, offset);
    if (skip_count == 0) {
      if (forwards) {
        offset++;
      }
      else {
        offset--;
      }
    }
    $('.word').text(part);
  },speed);
};

$(document).ready(function () {
  wordflick();
});


