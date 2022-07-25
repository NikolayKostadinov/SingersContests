(function () {
    const form = document.getElementById('file-upload');
    form.addEventListener('submit', formSubmitHandler);

    async function formSubmitHandler(event) {
        // Prevent default HTML page refresh
        event.preventDefault();

        // Get anti-forgery token
        const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
        const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content
        const btnUploadText = document.getElementById('upload-text');
        const spinner = document.getElementById('spinner');

        //Set spinner spin and disable upload button till finishing operation
        btnUploadText.hidden = true;
        spinner.hidden = false;
        spinner.parentElement.disabled = true;

        // Select file upload element
        const uploadElement = document.getElementById('file');

        // Extract the file (for a single file, always 0 in the list)
        const file = uploadElement.files[0];

        // Create new formData object then append file
        const payload = new FormData();
        payload.append('file', file);
        try {
            let response = await fetch(form.action, {
                method: "POST",
                headers: {
                    "Accept": "application/json",
                    [csrfHeaderName]: csrfHeaderValue
                },
                body: payload,
            });

            let data = await response.json();

            if (response.ok) {
                showData(data);
            } else {
                showError(data);
            }
        } catch (e) {
            console.log(e);
        }

        resetModal();
    }

    function showData(data) {
        const imgUrl = document.getElementById('imageUrl');
        const img = document.getElementById('picture-holder');
        img.src = data.url;
        imgUrl.value = data.url;
    }

    function showError(data) {
        const errModal = new bootstrap.Modal('#errorModal', {keyboard: true})
        document.getElementById('error-message').textContent = data.message ? data.message : 'Failed to upload file!';
        errModal.show();
    }

    function resetModal() {
        const fileInput = document.getElementById("file")
        fileInput.value = "";

        const btnUploadText = document.getElementById('upload-text');
        btnUploadText.hidden = false;
        const spinner = document.getElementById('spinner');
        spinner.hidden = true;
        spinner.parentElement.disabled = false;

        const modalElement = document.getElementById('chooseFileModal');
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
    }
})();

