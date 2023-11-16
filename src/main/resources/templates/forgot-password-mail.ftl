<html>
<head>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f8f8;
        }

        .container {
            background-color: #fff;
            border-radius: 10px;
            margin: 20px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
        }

        .header {
            background-color: #ff8c00;
            color: #fff;
            border-radius: 10px 10px 0 0;
            text-align: center;
            padding: 10px;
        }

        .content {
            padding: 20px;
        }

        .code {
            font-size: 24px;
            text-align: center;
            background-color: #d2d2d2;
            padding: 10px;
            border-radius: 5px;
            margin: 20px 0;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <img src="${imgSource}" alt="Logo" style="max-width: 100%;">
    </div>
    <div class="content">
        <p>If you forgot your password, please use the code below to reset it:</p>
        <div class="code">${code}</div>
        <p>If you did not request a password reset, please ignore this email.</p>
    </div>
    <div class="footer">
        <p>© 2023 Your Company. All rights reserved.</p>
    </div>
</div>
</body>
</html>
