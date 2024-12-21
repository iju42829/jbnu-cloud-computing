import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios'; 
import './RegisterPage.css'; 

const RegisterPage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
    });
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [id]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // (1) 클라이언트 단에서도 확인
        if (formData.password !== formData.confirmPassword) {
            setMessage('비밀번호가 일치하지 않습니다.');
            return;
        }

        try {
            // (2) 서버에 confirmPassword도 함께 전송
            //     POST /api/sign-up
            //     Body: { username, password, confirmPassword, email }
            const response = await axios.post(
                `${process.env.REACT_APP_BACKEND_URL}/api/sign-up`,
                {
                    username: formData.username,
                    password: formData.password,
                    confirmPassword: formData.confirmPassword,
                    email: formData.email
                }
            );

            // (3) 성공 시 서버에서 201 응답
            if (response.status === 201) {
                // 성공 메시지 표시 후 로그인 페이지 이동
                setMessage('회원가입 성공! 로그인 페이지로 이동합니다.');
                setTimeout(() => navigate('/login'), 2000);
            }
        } catch (error) {
            // (4) 서버에서 400 응답인 경우 등
            //     error.response?.status === 400 일 수도 있음
            if (error.response?.status === 400) {
                // 서버에서 잘못된 회원가입 요청 메시지
                setMessage(error.response.data?.message || '잘못된 요청입니다.');
            } else {
                // 기타 에러 처리
                setMessage(
                    error.response?.data?.message ||
                    '회원가입에 실패했습니다. 다시 시도해주세요.'
                );
            }
        }
    };

    return (
        <div className="register-container">
            <img src="image/logo.png" alt="Logo" className="logo" />
            <h2>회원 가입 하기</h2>
            <p className="subtitle">클라우드 박스 회원가입 페이지</p>

            <form onSubmit={handleSubmit}>
                <label htmlFor="username">아이디</label>
                <input
                    type="text"
                    id="username"
                    placeholder="example"
                    value={formData.username}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="email">이메일</label>
                <input
                    type="email"
                    id="email"
                    placeholder="example@mail.com"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="password">비밀번호</label>
                <input
                    type="password"
                    id="password"
                    placeholder="include *, %, ^ & (8~16)"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="confirmPassword">비밀번호 확인</label>
                <input
                    type="password"
                    id="confirmPassword"
                    placeholder=""
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    required
                />

                <button className="register-continue-btn" type="submit">
                    계정 생성하기
                </button>
            </form>

            {message && <p className="message">{message}</p>}

            <button className="register-login-btn" onClick={() => navigate("/login")}>
                계정이 있습니다
            </button>
        </div>
    );
};

export default RegisterPage;
