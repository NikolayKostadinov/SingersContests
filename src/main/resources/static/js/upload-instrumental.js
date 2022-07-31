(function () {
    const form = document.getElementById('instrumental-upload');
    const btnUploadInstrumentalElements = document.querySelectorAll('[role="instrumental-upload"]');
    // Select file upload element
    const uploadElement = form.querySelector('#file');

    Array.from(btnUploadInstrumentalElements).forEach(link => {
        link.addEventListener('click', attachResultElementToModal);
    });

    function attachResultElementToModal() {
        form.dataset.for = this.dataset.for;
        form.dataset.display = this.dataset.display;
        form.dataset.duration = this.dataset.duration;
        form.dataset.durationInSeconds = this.dataset.durationinseconds;

    }

    form.addEventListener('submit', formSubmitHandler);

    async function formSubmitHandler(event) {
        // Prevent default HTML page refresh
        event.preventDefault();

        // Get anti-forgery token
        const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
        const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content
        const btnUploadText = form.querySelector('#upload-text');
        const spinner = form.querySelector('#spinner');

        //Set spinner spin and disable upload button till finishing operation
        btnUploadText.hidden = true;
        spinner.hidden = false;
        spinner.parentElement.disabled = true;

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
            console.log(e)
            showError()
        }

        resetModal();
    }

    function showData(data) {
        const imgUrl = document.getElementById(form.dataset.for);
        const fileName = document.getElementById(form.dataset.display);
        fileName.value = data.fileName;
        imgUrl.value = data.url;
    }

    function showError(data) {
        const errModal = new bootstrap.Modal('#errorModal', {keyboard: true})
        document.getElementById('error-message').textContent = (data && data.message) ? data.message : 'Failed to upload file!';
        errModal.show();
    }

    function resetModal() {
        const fileInput = form.querySelector("#file")
        fileInput.value = "";

        const btnUploadText = form.querySelector('#upload-text');
        btnUploadText.hidden = false;
        const spinner = form.querySelector('#spinner');
        spinner.hidden = true;
        spinner.parentElement.disabled = false;

        const modalElement = form.closest('.modal');
        //const modalElement = document.getElementById('chooseFileModal');
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
    }

    // Create a non-dom allocated Audio element
    var audio = document.createElement('audio');

// Add a change event listener to the file input
    uploadElement.addEventListener('change', function(event){
        let target = event.currentTarget;
        let file = target.files[0];
        let reader = new FileReader();

        if (target.files && file) {
            let reader = new FileReader();

            reader.onload = function (e) {
                audio.src = e.target.result;
                audio.addEventListener('loadedmetadata', function(){
                    // Obtain the duration in seconds of the audio file (with milliseconds as well, a float value)
                    let duration = audio.duration;
                    let displayDuration = Math.floor(duration / 60) + ':' + Math.floor(duration % 60);
                    const durationDisplay = document.getElementById(form.dataset.duration);
                    const durationValue = document.getElementById(form.dataset.durationInSeconds);
                    durationDisplay.value = displayDuration;
                    durationValue.value = Math.floor(duration);

                },false);
            };

            reader.readAsDataURL(file);
        }
    }, false);
})();
