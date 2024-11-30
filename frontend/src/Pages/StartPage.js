import React from "react";
import { useNavigate } from "react-router-dom";

const StartPage = () => {
  const navigate = useNavigate();
  return (
    <div>
      <button onClick={()=> navigate("/register")}>회원가입</button>
      <button onClick={()=> navigate("/login")}>로그인</button>
    </div>
  )
}

export default StartPage;