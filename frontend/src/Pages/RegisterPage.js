import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function RegisterPage() {
  const navigate = useNavigate();
  return(
    <button onClick={()=> navigate("/login")}>확인</button>
  )
}
export default RegisterPage;