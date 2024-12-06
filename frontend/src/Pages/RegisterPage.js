import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './RegisterPage.css';

const RegisterPage = () => {
  return (
    <div className="signup-container">
      {/* Logo */}
      <img src="logo.png" alt="Logo" className="logo" />

      {/* Title */}
      <h2>CREATE AN ACCOUNT</h2>
      <p className="subtitle">signup page for cloud box</p>

      {/* ID Input */}
      <label htmlFor="id">ID</label>
      <input type="text" id="id" placeholder="example" />

      {/* Email Input */}
      <label htmlFor="email">EMAIL</label>
      <input type="email" id="email" placeholder="example@mail.com" />

      {/* Password Input */}
      <label htmlFor="password">PASSWORD</label>
      <input type="password" id="password" placeholder="include *, %, ^ & (8~16)" />

      {/* Confirm Password Input */}
      <label htmlFor="confirm-password">CONFIRM PASSWORD</label>
      <input type="password" id="confirm-password" placeholder="" />

      {/* Continue Button */}
      <button className="continue-btn">
        <div>
          <span>C</span><span>O</span><span>N</span><span>T</span><span>I</span><span>N</span><span>U</span><span>E</span>
        </div>
      </button>

      {/* Back to Login Button */}
      <button className="back-to-login-btn" onClick={() => window.location.href = 'login.html'}>
        <div>
          <span>B</span><span>A</span><span>C</span><span>K</span><span> </span><span>T</span><span>O</span><span> </span><span>L</span><span>O</span><span>G</span><span>I</span><span>N</span>
        </div>
      </button>
    </div>
  );
};

export default RegisterPage;