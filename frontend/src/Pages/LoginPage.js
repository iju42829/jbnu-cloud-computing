import React from 'react';
import './LoginPage.css';

const LoginPage = () => {
  return (
    <div className="login-container">
      {/* Logo */}
      <img src="image/logo.png" alt="Logo" className="logo" />

      {/* Title */}
      <h2>LOGIN</h2>
      <p className="subtitle">login page for cloud box</p>

      {/* ID Input */}
      <label htmlFor="id">ID</label>
      <input type="text" id="id" placeholder="ID" />

      {/* Password Input */}
      <label htmlFor="password">PASSWORD</label>
      <input type="password" id="password" placeholder="Password" />

      {/* Forgot Password Link */}
      <a href="#" className="forgot-password">FORGOT YOUR PASSWORD?</a>

      {/* Login Button */}
      <button className="login-btn">
        <div>
          <span>L</span><span>O</span><span>G</span><span>I</span><span>N</span>
        </div>
      </button>

      {/* Signup Button */}
      <button className="signup-btn" onClick={() => window.location.href = 'signup.html'}>
        <div>
          <span>S</span><span>I</span><span>G</span><span>N</span><span> </span><span>U</span><span>P</span>
        </div>
      </button>

      {/* Separator */}
      <div className="separator">LOGIN AS SOCIAL MEDIA</div>

      {/* Social Login Buttons */}
      <div className="social-btn google">
        <img src="image/google-icon.png" alt="Google Logo" />
        Login with Gmail
      </div>
      <div className="social-btn naver">
        <img src="image/naver-icon.png" alt="Naver Logo" />
        Login with Naver
      </div>
    </div>
  );
};

export default LoginPage;