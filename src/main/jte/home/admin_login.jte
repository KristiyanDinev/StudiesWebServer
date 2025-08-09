<header>
    @template.layouts._header(title = "Admin Login")
</header>
<body class="bg-light">
<div class="container vh-100 d-flex align-items-center justify-content-center">
    <div class="row w-100">
        <div class="col-12 col-md-6 col-lg-4 mx-auto">
            <div class="card shadow-lg border-0">
                <div class="card-body p-5">
                    <div class="text-center mb-4">
                        <i class="bi bi-shield-lock-fill text-primary fs-1 mb-3"></i>
                        <h2 class="card-title text-dark fw-bold">Access Token</h2>
                        <p class="text-muted">Enter your authentication token to continue</p>
                    </div>

                    <div>
                        <div class="form-floating mb-4">
                            <input type="text"
                                   class="form-control form-control-lg"
                                   id="token"
                                   placeholder="Enter your token"
                                   required
                                   autocomplete="off">
                            <label for="token">
                                <i class="bi bi-lock-fill me-2"></i>Authentication Token <span class="text-danger">*</span>
                            </label>
                        </div>

                        <div id="error" class="alert d-none" role="alert"></div>

                        <div class="d-grid">
                            <button type="button" class="btn btn-primary btn-lg" id="submitBtn">
                                <i class="bi bi-arrow-right-circle me-2"></i>
                                Authenticate
                            </button>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <small class="text-muted">
                            <i class="bi bi-info-circle me-1"></i>
                            Need help? Contact your administrator
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

@template.layouts._footer()
<script src="/assets/home/js/login.js"></script>
</body>