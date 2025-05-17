document.getElementById("uploadButton").addEventListener("click", function () {
    console.log("Upload button clicked!");
    document.getElementById("backgroundImage").click();
});

document.getElementById("backgroundImage").addEventListener("change", function (event) {
    console.log("File selected!");
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            console.log("File read successfully!");
            const photoPreview = document.getElementById("photoPreview");
            photoPreview.src = e.target.result;

            photoPreview.onload = function () {
                photoPreview.style.height = "auto";
                photoPreview.style.maxHeight = "400px";
            };
        };

        reader.readAsDataURL(file);
    }
});

