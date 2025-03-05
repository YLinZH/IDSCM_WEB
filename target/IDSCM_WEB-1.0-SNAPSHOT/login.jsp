<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/login.css">
    </head>
    <body>

        <div class="container">
            <div class="login-container">
                <h3 class="text-center">Welcome</h3>
                <p class="text-center text-muted">Please login to continue</p>
                
                 <!-- Mensaje de error -->
                <% 
                    String errorMessage = (String) request.getAttribute("error");
                    if (errorMessage != null) { 
                %>
                    <div class="alert alert-danger text-center" role="alert">
                        <%= errorMessage %>
                    </div>
                <% } %>
                
                <form action="login" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Login</button>
                    <p class="text-center mt-3">
                        <a href="#">Forgot password?</a>
                    </p>
                </form>
                
                <div class="text-center mt-2">
                    <a href="registroUsu.jsp">Create an Account</a>
                </div>
            </div>
        </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>

