var popUpFlag = true
function onclickPopUpUpload(){
	console.log("hay");
	var elemPopUp = document.getElementById("popUpUpload");
	if(popUpFlag == true)
		elemPopUp.style.visibility = "visible";
	else 
		elemPopUp.style.visibility = "hidden";
	popUpFlag = ~popUpFlag;
}