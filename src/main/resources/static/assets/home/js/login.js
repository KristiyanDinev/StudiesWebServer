var invalidClass = "is-invalid"

document.getElementById('submitBtn').addEventListener("click", async function(event) {
        event.preventDefault()
        let tokenElement = document.getElementById('token')
        let errorElement = document.getElementById('error')

        const token = tokenElement.value

        errorElement.className = "alert d-none"
        errorElement.innerHTML = ""
        tokenElement.classList.remove(invalidClass)
        if (!token) {
            errorElement.className = 'alert alert-warning'
            errorElement.innerHTML = '<i class="bi bi-exclamation-triangle-fill me-2"></i>Please fill in the token'
            tokenElement.classList.add(invalidClass)
            return
        }

        const submitButton = document.getElementById('submitBtn')
        const originalText = submitButton.innerHTML
        submitButton.innerHTML = '<i class="bi bi-arrow-clockwise me-2"></i>Verifying...'
        submitButton.disabled = true

        let formData = new FormData()
        formData.append('token', token)
        try {
            const res = await fetch('/home/admin_login', {
                method: 'POST',
                body: formData
            })

            if (!res.ok) {
                throw new Error()
            }
            errorElement.innerHTML = ''
            tokenElement.classList.remove(invalidClass)
            window.location.reload()


        } catch {
        errorElement.className = 'alert alert-danger'
                errorElement.innerHTML = '<i class="bi bi-x-circle-fill me-2"></i>Invalid Token'
                tokenElement.classList.add(invalidClass)
                tokenElement.value = ''

                submitButton.innerHTML = originalText
                submitButton.disabled = false
        }
})
