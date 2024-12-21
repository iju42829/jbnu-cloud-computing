import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './LoginPage.css';

const LoginPage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: '',
        password: ''
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
            // 추가: headers와 withCredentials 설정
            const response = await axios.post(
                `${process.env.REACT_APP_BACKEND_URL}/api/login`,
                {
                    username: formData.username,
                    password: formData.password
                },
                {
                    headers: {
                        'Content-Type': 'application/json' // Content-Type 명시
                    },
                    withCredentials: true // 쿠키 기반 인증 필요 시 추가
                }
            );

            if (response.status === 200) {
                setMessage('로그인 성공! 메인 페이지로 이동합니다.');
                setTimeout(() =>
                    navigate('/main', {
                        state: { userInfo: response.data.data } // 상태 전달
                    }), 2000);
            }
        } catch (error) {
            if (error.response?.status === 401) {
                setMessage('인증 실패: 잘못된 사용자 이름 또는 비밀번호입니다.');
            } else {
                setMessage('로그인에 실패했습니다. 다시 시도해주세요.');
            }
        }
    };
   
    const handleSocialLogin = (platform) => {
        alert(`${platform} 현재 구현되지 않은 기능입니다.`);
    };

    const handleForgotPassword = () => {
        alert('비밀번호 찾기 기능은 현재 구현되지 않은 기능입니다.');
    };

    return (
        <div className="login-container">
            {/* 로고 */}
            <img src="image/logo.png" alt="Logo" className="logo" />

            {/* 제목 */}
            <h2>로그인</h2>
            <p className="subtitle">클라우드 박스 로그인 페이지</p>

            <form onSubmit={handleSubmit}>
                {/* 사용자 이름 입력 */}
                <label htmlFor="username">아이디</label>
                <input
                    type="text"
                    id="username"
                    placeholder="아이디를 입력하세요"
                    value={formData.username}
                    onChange={handleChange}
                    required
                />

                {/* 비밀번호 입력 */}
                <label htmlFor="password">비밀번호</label>
                <input
                    type="password"
                    id="password"
                    placeholder="비밀번호를 입력하세요"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />

                {/* 로그인 버튼 */}
                <button className="login-btn" type="submit">
                    로그인
                </button>
            </form>

            {/* 메시지 출력 */}
            {message && <p className="message">{message}</p>}

            {/* 회원가입 버튼 */}
            <button className="signup-btn" onClick={() => navigate('/register')}>
                회원가입하기
            </button>

            {/* 비밀번호 찾기 */}
            <a
                href="#"
                className="forgot-password"
                onClick={handleForgotPassword}
            >
                비밀번호를 잊으셨나요?
            </a>

            {/* 구분선 */}
            <div className="separator">소셜미디어 로그인</div>

            {/* 소셜 로그인 버튼 */}
            <div className="social-btn google" onClick={() => handleSocialLogin("Google")}>
                <img src="image/google-icon.png" alt="Google Logo" />
                지메일로 로그인
            </div>
            <div className="social-btn naver" onClick={() => handleSocialLogin("Naver")}>
                <img src="image/naver-icon.png" alt="Naver Logo" />
                네이버로 로그인
            </div>
        </div>
    );
};

export default LoginPage;
