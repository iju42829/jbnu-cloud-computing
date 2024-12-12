import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
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
        try {
            const response = await axios.post(`${process.env.REACT_APP_BACKEND_URL}/api/sign-up`, {
                username: formData.username,
                email: formData.email,
                password: formData.password,
                confirmPassword: formData.confirmPassword
            });

            if (response.status === 201) {
                setMessage('회원가입이 성공적으로 완료되었습니다! 로그인 페이지로 이동합니다.');
                setTimeout(() => navigate('/login'), 2000);
            }
        } catch (error) {
            setMessage(error.response?.data?.message || '회원가입에 실패했습니다. 다시 시도해주세요.');
        }
    };

    return (
        <div className="register-container">
            {/* 로고 */}
            <img src="image/logo.png" alt="Logo" className="logo" />

            {/* 제목 */}
            <h2>회원 가입 하기</h2>
            <p className="subtitle">클라우드 박스 회원가입 페이지</p>

            <form onSubmit={handleSubmit}>
                {/* ID 입력 */}
                <label htmlFor="username">아이디</label>
                <input
                    type="text"
                    id="username"
                    placeholder="example"
                    value={formData.username}
                    onChange={handleChange}
                    required
                />

                {/* 이메일 입력   */}
                <label htmlFor="email">이메일</label>
                <input
                    type="email"
                    id="email"
                    placeholder="example@mail.com"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />

                {/* 비밀번호 입력 */}
                <label htmlFor="password">비밀번호</label>
                <input
                    type="password"
                    id="password"
                    placeholder="include *, %, ^ & (8~16)"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />

                {/* 비밀번호 확인 입력 */}
                <label htmlFor="confirmPassword">비밀번호 확인</label>
                <input
                    type="password"
                    id="confirmPassword"
                    placeholder=""
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    required
                />

                {/* 회원가입 버튼 */}
                <button className="register-continue-btn" type="submit">
                    계정 생성하기
                </button>
            </form>

            {/* 메시지 출력 */}
            {message && <p className="message">{message}</p>}

            {/* 로그인 페이지 이동 버튼 */}
            <button className="register-login-btn" onClick={() => navigate('/login')}>
                계정이 있습니다
            </button>
        </div>
    );
};

export default RegisterPage;
