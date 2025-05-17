document.getElementById("uploadProfilePhotoButton").addEventListener("click", function () {

    document.getElementById("profilePhotoInput").click();
});

document.getElementById("profilePhotoInput").addEventListener("change", function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const profilePhotoPreview = document.getElementById("profilePhotoPreview");
            profilePhotoPreview.src = e.target.result;
            profilePhotoPreview.onload = function () {
                profilePhotoPreview.style.height = "auto";
                profilePhotoPreview.style.maxHeight = "200px";
            };
        };
        reader.readAsDataURL(file);
    }
});