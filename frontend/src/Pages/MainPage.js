import React, { useState } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import './MainPage.css'
 
  //로그인 창에서 넘어옴
  //사용자 정보(이메일, 이름, 프로필 사진(?)), 파일 저장소 정보, 옵션 정보를 받아옴
  //헤더의 사용자 부분을 사용자 정보로 초기화
  //메인 컨테이너의 내용을 파일 저장소 정보로 초기화
  //최근 파일의 최대 개수 제한
  //초기화 시'내 저장소'로 이동하도록 함
  //페이지 새로고침 시 다시 초기화하도록 함
  //사이드 컨테이너의 내 저장소 부분을 초기화
  //내 저장소의 구성을 확인할 수 있도록 함.
  //파일 제외, 폴더만 표시.
  //옵션 파일의 내용에 맞춰 사용자 설정 초기화(전체 뷰에 관여)
  //옵션이 변경될 경우 확인 버튼을 눌렀을 때 옵션 파일의 내용을 수정하고, 업데이트하는 형식


function MainPage() {

  //페이지 이동, 이전 페이지의 데이터 가져오기
  const navigate = useNavigate();  
  const location = useLocation();
  const {userInfo, storageInfo, options} = location.state || {};

  const [files, setFiles] = useState(storageInfo || []);

  //현재 위치 관리
  const Path = "/내 저장소"

  //드롭다운 관리
  const [isDropdownView, setDropdownView] = useState(false);
  const viewDropdown = () => {
    setDropdownView((isDropdownView) => !isDropdownView);
    console.log("Dropdown state:", !isDropdownView);
  }

  const [expandedFolders, setExpandedFolders] = useState({}); // 폴더 열림 상태 관리

  // 폴더 열림/닫힘 토글
  const toggleFolder = (path) => {
    setExpandedFolders((prevState) => ({
      ...prevState,
      [path]: !prevState[path], // 현재 경로의 열림 상태를 토글
    }));
  };

  // 폴더 계층 구조 렌더링
  const renderFolders = (folderList, parentPath = "") => {
    return folderList.map((item) => {
      if (item.type === "folder") {
        const currentPath = `${parentPath}/${item.name}`;
        return (
          <div key={currentPath} className="folder-item">
            <button className="storage-item" onClick={() => toggleFolder(currentPath)}>
              <img
                src={`image/${expandedFolders[currentPath] ? "fold" : "spread"}.png`}
                alt="folder-icon"
                className="sideimg"
              />
              {item.name}
            </button>
            {expandedFolders[currentPath] && item.children && (
              <div className="nested-folders">
                {renderFolders(item.children, currentPath)}
              </div>
            )}
          </div>
        );
      }
      return null; // 폴더가 아닌 파일은 사이드 메뉴에서 표시하지 않음
    });
  };

  //팝업창 관리
  const [isFolderPopupOpen, setFolderPopupOpen] = useState(false);
  const [folderName, setFolderName] = useState("");
  const openFolderPopup = () => {
    setFolderPopupOpen(true);
  };

  const closeFolderPopup = () => {
    setFolderPopupOpen(false);
    setFolderName(""); // 입력 필드 초기화
  };

  const handleCreateFolder = () => {
    console.log(`새 폴더 이름: ${folderName}`);
    if (folderName.trim() === '') {
      alert("폴더 이름을 입력하세요.")
      return;
    }
    const newFolder = {
      name: folderName,
      type: "folder",
      children: []
    };

    setFiles((prevFiles) => [...prevFiles, newFolder]);

    // 데이터베이스 적용 로직 추가
    closeFolderPopup(); // 팝업 닫기
  };
  const [isSettingPopupOpen, setSettingPopupOpen] = useState(false);
  const openSettingPopup = () => {
    setSettingPopupOpen(true);
    console.log("open :", isSettingPopupOpen);
  }
  const closeSettingPopup = () => {
    //옵션 설정
    setSettingPopupOpen(false);
  }
  // 파일 업로드 핸들러
  const handleFileUpload = (event) => {
    const uploadedFiles = Array.from(event.target.files); // 선택된 파일 배열
    const newFiles = uploadedFiles.map((file) => ({
      name: file.name,
      size: file.size,
      type: file.type,
      lastModified: file.lastModified,
    }));
    setFiles((prevFiles) => [...prevFiles, ...newFiles]); // 기존 파일 목록에 추가
    //업로드
  };

  //정렬 관리
  const [sortKey, setSortKey] = useState("name"); // 기본 정렬 기준
  const [sortOrder, setSortOrder] = useState("asc"); // 기본 정렬 순서
  const handleSort = (key) => {
    if (sortKey === key) {
      // 동일 키 클릭 시 정렬 방향 토글
      setSortOrder((prevOrder) => (prevOrder === "asc" ? "desc" : "asc"));
    } else {
      // 다른 키 클릭 시 정렬 기준 변경 및 오름차순 초기화
      setSortKey(key);
      setSortOrder("asc");
    }
  };
  const sortedFiles = [...files].sort((a, b) => {
    // 폴더 우선 정렬
    if (a.type === "folder" && b.type !== "folder") return -1;
    if (a.type !== "folder" && b.type === "folder") return 1;

    let valueA = a[sortKey];
    let valueB = b[sortKey];

    // 특정 키에 대한 비교
    if (sortKey === "lastModified") {
      valueA = new Date(valueA || 0);
      valueB = new Date(valueB || 0);
    } else if (sortKey === "size") {
      valueA = valueA || 0;
      valueB = valueB || 0;
    }

    if (valueA < valueB) return sortOrder === "asc" ? -1 : 1;
    if (valueA > valueB) return sortOrder === "asc" ? 1 : -1;
    return 0;
  });

  const [selectedFiles, setSelectedFiles] = useState([]); // 선택된 파일/폴더
  const [previewFile, setPreviewFile] = useState(null); // 미리보기 파일

  // 클릭 시 강조 표시
  const handleContainerClick = (event) => {
    // 클릭한 곳이 파일/폴더가 아닌 경우에만 선택 해제
    if (!event.target.closest("tr")) {
      setSelectedFiles([]);
    }
  };

  const handleClick = (file, event) => {
    if (event.ctrlKey) {
      // Ctrl 누르고 클릭하면 다중 선택
      setSelectedFiles((prevSelected) =>
        prevSelected.includes(file)
          ? prevSelected.filter((f) => f !== file) // 이미 선택된 경우 해제
          : [...prevSelected, file]
      );
    } else {
      // 단일 선택
      setSelectedFiles([file]);
    }
  };

  // 더블 클릭 이벤트
  const handleDoubleClick = (file) => {
    if (file.type === "folder") {
      // 폴더인 경우 해당 경로로 진입
    } else {
      // 파일인 경우 미리보기 표시
      setPreviewFile(file);
    }
  };

  return (
    <body onClick={handleContainerClick}>
        {/* 헤더 */}
        <header>
          <button className="logobtn">
            <img src="image/logo.png" className="logoimg"/>
            <h className="logofont">CLOUDBOX</h>
          </button>        
          <div className="searchbar">
            <img src="image/search.png" className="searchimg" />
            <input className="searchtext" placeholder="검색"></input>
          </div>
          <>
            {isDropdownView && <button className="userbtn">계정관리</button>}
            {isDropdownView && <button className="userbtn" onClick={() => navigate('/')}>로그아웃</button>}
            <button className="userbtn" onClick={viewDropdown}>
              <img src={userInfo?.profileImage} onError={(e) => {e.target.onerror = null; 
                e.target.src = "image/user.png"}} className="headerimg"/> 
              <h>{userInfo?.name}</h>
            </button>
          </>
        </header>
        {/* 메인 부분 */}
        <main>
          {/* 사이드 메뉴 */}
          <div className="side-container">
            <button className="side-menu" onClick={openFolderPopup}>
              <img src="image/add-folder.png" className="sideimg" />새 폴더
            </button>
            <button className="side-menu">
              <img src="image/file-upload.png" className="sideimg" />
              <label htmlFor="file-upload" style={{ cursor: "pointer" }}>
                파일 업로드
              </label>
              <input
                id="file-upload"
                type="file"
                multiple
                style={{ display: "none" }}
                onChange={handleFileUpload}
              />
            </button>
            <div className="storage-viewer">
              <button className="storage-item" onClick={() => toggleFolder("/내 저장소")}>
                <img src={`image/${expandedFolders["/내 저장소"] ? "fold" : "spread"}.png`}  className="sideimg" />
                <h >내 저장소</h>                 
              </button>
              {expandedFolders["/내 저장소"] && (
                <div className="nested-folders">
                  {renderFolders(files, "/내 저장소")}
                </div>
              )}
            </div>
            <button className="side-menu" >
              <img src="image/trash.png" className="sideimg" />휴지통
            </button>
            <button className="side-menu" onClick={openSettingPopup}>
              <img src="image/setting.png" className="sideimg" />설정
            </button>
          </div>
          {/* 메인 컨테이너 */}
          <div className="main-container">
            {/* 최근 파일 부분
            <div className="recent-container">최근 파일 </div> */}
            {/* 파일 부분 */}
            <div className="all-file-container">
              {/* 현재 표시되는 파일 경로 */}
              <h >{Path}</h>
              <table>
                <thead>
                  <tr>
                    <th></th>
                    <th onClick={() => handleSort("name")}>
                      파일 이름 {sortKey === "name" && (sortOrder === "asc" ? "▲" : "▼")}
                    </th>
                    <th onClick={() => handleSort("size")}>
                      파일 크기 {sortKey === "size" && (sortOrder === "asc" ? "▲" : "▼")}
                    </th>
                    <th>파일 유형</th>
                    <th onClick={() => handleSort("lastModified")}>
                      마지막 수정 날짜 {sortKey === "lastModified" && (sortOrder === "asc" ? "▲" : "▼")}
                    </th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                {sortedFiles.map((file, index) => (
                  <tr key={index} onClick={(e) => handleClick(file, e)}
                  onDoubleClick={() => handleDoubleClick(file)}
                  className={selectedFiles.includes(file) ? "selected" : ""}>
                    <td className="file-icon-column">
                      <img src={`image/${file.type === "folder" ? "folder" : "file"}.png`}
                        alt={file.type} className="file-icon"/>
                    </td>
                    <td>{file.name}</td>
                    <td>{file.type === "folder" ? "-" : `${(file.size / 1024).toFixed(2)} KB`}</td>
                    <td>{file.type}</td>
                    <td>{file.type === "folder" ? "-" : new Date(file.lastModified).toLocaleString()} </td>
                    <td className="menu-btn-column">
                      <button className="menu-btn">
                        <img src="image/menu.png" alt="Menu" />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
              </table>
            </div>
          </div>
        </main>

        {/* 새 폴더 팝업 */}
        {isFolderPopupOpen && (
          <div className="popup-overlay">
            <div className="popup">
              <h2>새 폴더 만들기</h2>
              <input
                type="text"
                placeholder="폴더 이름을 입력하세요"
                value={folderName}
                onChange={(e) => setFolderName(e.target.value)}
                className="popup-input"
              />
              <div className="popup-buttons">
                <button onClick={handleCreateFolder} className="popup-btn">
                  확인
                </button>
                <button onClick={closeFolderPopup} className="popup-btn">
                  취소
                </button>
              </div>
            </div>
          </div>
        )}
        {/* 설정 팝업 */}
        {isSettingPopupOpen && (
          <div class="popup-overlay">
            <div class="popup-setting">
              <div class="setting-menu">
                <button> 계정설정</button>
                <button> 환경설정</button>
              </div>
              <div class="setting-main">
                <h>설정</h>
                <h>체크박스 + 설정 내용</h>
                <button class="popup-btn" onClick={closeSettingPopup}>닫기</button>
              </div>
            </div>
          </div>
        )}
        {/* 미리보기 창 */}
        {previewFile && (
          <div className="preview-overlay" onClick={() => setPreviewFile(null)}>
            <div className="preview-container">
              <h2>미리보기: {previewFile.name}</h2>
              <p>파일 유형: {previewFile.type}</p>
              <p>파일 크기: {(previewFile.size / 1024).toFixed(2)} KB</p>
              <p>마지막 수정: {new Date(previewFile.lastModified).toLocaleString()}</p>
              <button onClick={() => setPreviewFile(null)}>닫기</button>
            </div>
          </div>
        )}
    </body>
  );
}

export default MainPage;
