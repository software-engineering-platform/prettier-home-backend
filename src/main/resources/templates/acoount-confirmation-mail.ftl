<html>
<head>
    <style>

        .container {
            font-family: Verdana, sans-serif;
            background-color: #fac769;
            border-radius: 10px;
            margin: 20px auto;
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1), 0 2px 5px rgba(0, 0, 0, 0.3);
            width: 80%;
        }

        .header {
            background-color: #fac769;
            color: #fff;
            border-radius: 10px 10px 0 0;
            text-align: center;
            padding: 20px;
        }

        .content {
            background-color: #F8F8FF;
            border: 2px solid #fac769;

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
            font-size:14px;
            background-color: #fac769;
            border-radius: 0 0 10px 10px;
            text-align: center;
            padding: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <img src="${imgSource}" alt="Logo" style="max-width: 100%;">
    </div>
    <div class="content">
    <
        <p>Thank you for registering. Please click on the below link to activate your account.</p>
        <button class="code">${code}</button>
        <p>This link will expire in 24 hours!</p>
    </div>
    <div class="footer">
        <p>© 2023 Prettier Homes LLC. All rights reserved.</p>
    </div>
</div>
</body>
</html>