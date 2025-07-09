
<header>
    @template.layouts._header(title = "Login")
</header>
<body>
    <div class="d-flex container align-items-center">
        <h1>Token</h1>
        <div class="form-floating">
            <input type="text" class="form-control" id="token" placeholder="Enter the login token" required>
            <label for="token">
                <i class="bi bi-lock-fill me-2"></i>Token
            </label>
        </div>
    </div>

    @template.layouts._footer()
 </body>
