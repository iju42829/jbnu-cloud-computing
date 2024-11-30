import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";


function LoginPage() {
  const navigate = useNavigate();

  const [userInfo, setUserInfo] = useState({
    email: "user@example.com",
    name: "pikachu",
    profileImage: "image/025.png"
  });

  const [storageInfo, setStorageInfo] = useState([]);
  useEffect(() => {
    fetch("/mockData.json").then((response) => response.json()).then((data) => setStorageInfo(data))
    .catch((error) => console.error("Load Error", error));
  }, []);

  const [options, setOptions] = useState({theme: "dark", notifications: true });


  return(
    <button onClick={()=> navigate("/main", {state: {userInfo, storageInfo, options}})}>확인</button>
  )
}
export default LoginPage;