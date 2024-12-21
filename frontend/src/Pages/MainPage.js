import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import './MainPage.css';

function MainPage() {
  const navigate = useNavigate();  
  const location = useLocation();

  // 로그인 성공 시 location.state로부터 userInfo, options를 받는다고 가정
  const { userInfo } = location.state || {};

  const [folderHistory, setFolderHistory] = useState([ userInfo.rootFolderId ]);

  // 현재 폴더 ID
  const currentFolderId = folderHistory[folderHistory.length - 1];

  // 2) 파일/폴더 목록 상태
  const [files, setFiles] = useState([]);
  const [folders, setFolders] = useState([]);

  // 검색 상태
  const [searchQuery, setSearchQuery] = useState("");

  // 팝업/선택된 리소스
  const [isMovePopupOpen, setMovePopupOpen] = useState(false);
  const [targetFolderId, setTargetFolderId] = useState("");
  const [selectedResource, setSelectedResource] = useState(null);

  // 폴더 생성 팝업
  const [isFolderPopupOpen, setFolderPopupOpen] = useState(false);
  const [folderName, setFolderName] = useState("");

  // 기타 UI 상태
  const [isDropdownView, setDropdownView] = useState(false);
  const [openStorage, setOpenStorage] = useState({});
  const [isSettingPopupOpen, setSettingPopupOpen] = useState(false);

  // 파일 선택/미리보기
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [previewFile, setPreviewFile] = useState(null);

  // 정렬 상태
  const [sortKey, setSortKey] = useState("name");
  const [sortOrder, setSortOrder] = useState("asc");

  // -----------------------------
  // 3) 컴포넌트가 마운트된 후, userInfo?.rootFolderId로 fetchFiles
  // -----------------------------    
  useEffect(() => {
    // 서버에서 받은 rootFolderId
    if (userInfo?.rootFolderId !== undefined) {
      fetchFiles(userInfo.rootFolderId);
    } else {
      // 백업 수단 (ex: 1 또는 999, 실제 서버에서 유효한 값)
      fetchFiles(1);
    }
  }, [userInfo]);

  // -----------------------------
  // 폴더 / 파일 목록 조회 (GET /api/main?folderId=...)
  // -----------------------------
  const fetchFiles = async (folderId) => {
    try {
      const response = await fetch(`/api/main?folderId=${folderId}`);
      if (!response.ok) throw new Error("파일 목록 조회 실패");
      const result = await response.json();

      const foldersData = result.data.folders || [];
      const filesData = result.data.files || [];

      // 폴더 매핑
      const mappedFolders = foldersData.map(folder => ({
        name: folder.folderName,
        type: "folder",
        size: null,
        lastModified: folder.lastModifiedDate,
        id: folder.folderId
      }));

      // 파일 매핑
      const mappedFiles = filesData.map(file => ({
        name: file.fileName,
        type: "file",
        size: file.size,
        lastModified: file.lastModifiedDate,
        id: file.fileId
      }));

      setFolders(mappedFolders);
      setFiles(mappedFiles);

      // folderHistory의 마지막 요소를 현재 folderId로 갱신
      setFolderHistory(prev => {
        const newHist = [...prev];
        newHist[newHist.length - 1] = folderId;
        return newHist;
      });
    } catch (error) {
      console.error(error);
      alert("파일 목록을 불러오는 중 오류가 발생했습니다.");
    }
  };

  // 폴더 더블 클릭 → 하위 폴더 이동
  const handleDoubleClick = (item) => {
    if (item.type === "folder") {
      // folderHistory에 새 폴더 ID push
      setFolderHistory(prev => [...prev, item.id]);
      fetchFiles(item.id);
    } else {
      // 파일이면 미리보기
      setPreviewFile(item);
    }
  };

  // 상위 폴더로 이동
  const handleGoBack = () => {
    if (folderHistory.length > 1) {
      setFolderHistory(prev => {
        const newHistory = [...prev];
        newHistory.pop();
        fetchFiles(newHistory[newHistory.length - 1]);
        return newHistory;
      });
    } else {
      alert("상위 폴더가 없습니다.");
    }
  };

  // 파일 업로드 (POST /api/file?folderId=...)
  const uploadFile = async (file, folderId) => {
    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch(`/api/file?folderId=${folderId}`, {
        method: "POST",
        body: formData,
      });
      if (!response.ok) throw new Error("파일 업로드 실패");
      await response.json();
      alert("파일이 업로드되었습니다.");
      fetchFiles(folderId);
    } catch (error) {
      console.error(error);
      alert("파일 업로드 중 오류가 발생했습니다.");
    }
  };

  const handleFileUpload = async (event) => {
    const uploadedFiles = Array.from(event.target.files);
    for (const file of uploadedFiles) {
      try {
        await uploadFile(file, currentFolderId);
      } catch (error) {
        console.error("파일 업로드 중 오류:", error);
      }
    }
  };

  // 파일 다운로드 (GET /api/file/{fileId})
  const downloadFile = async (fileId, fileName) => {
    try {
      const response = await fetch(`/api/file/${fileId}`);
      if (!response.ok) throw new Error("파일 다운로드 실패");
      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
      alert(`${fileName} 파일이 다운로드되었습니다.`);
    } catch (error) {
      console.error(error);
      alert("파일 다운로드 중 오류가 발생했습니다.");
    }
  };

  // 파일 삭제 (DELETE /api/file/{fileId})
  const deleteFile = async (fileId) => {
    try {
      const response = await fetch(`/api/file/${fileId}`, { method: "DELETE" });
      if (!response.ok) throw new Error("파일 삭제 실패");
      alert("파일이 삭제되었습니다.");
      fetchFiles(currentFolderId);
    } catch (error) {
      console.error(error);
      alert("파일 삭제 중 오류가 발생했습니다.");
    }
  };

  // 폴더 생성 (POST /api/folders/{parentFolderId})
  const createFolder = async (name) => {
    const parentFolderId = currentFolderId; 
    try {
      const response = await fetch(`/api/folders/${parentFolderId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name }),
      });
      if (!response.ok) throw new Error("폴더 생성 실패");
      await response.json();
      alert("폴더가 생성되었습니다.");
      fetchFiles(parentFolderId);
    } catch (error) {
      console.error(error);
      alert("폴더 생성 중 오류가 발생했습니다.");
    }
  };

  const handleCreateFolder = async () => {
    if (!folderName.trim()) {
      alert("폴더 이름을 입력하세요.");
      return;
    }
    try {
      await createFolder(folderName.trim());
      setFolderPopupOpen(false);
      setFolderName("");
    } catch (error) {
      console.error("폴더 생성 오류:", error);
    }
  };

  // 폴더 삭제 (DELETE /api/folders/{folderId})
  const deleteFolder = async (folderId) => {
    try {
      const response = await fetch(`/api/folders/${folderId}`, {
        method: "DELETE",
      });
      if (!response.ok) throw new Error("폴더 삭제 실패");
      alert("폴더가 삭제되었습니다.");
      fetchFiles(currentFolderId);
    } catch (error) {
      console.error(error);
      alert("폴더 삭제 중 오류가 발생했습니다.");
    }
  };

  // 파일 검색 (GET /api/file/search?filename=...)
  const searchFiles = async (query) => {
    try {
      const response = await fetch(`/api/file/search?filename=${encodeURIComponent(query)}`);
      if (!response.ok) throw new Error("파일 검색 실패");
      const result = await response.json();
      setFiles(result.data.map(file => ({
        name: file.fileName,
        type: "file",
        size: file.size,
        lastModified: file.lastModifiedDate,
        id: file.fileId,
      })));
    } catch (error) {
      console.error(error);
      alert("파일 검색 중 오류가 발생했습니다.");
    }
  };

  // 공유 파일 관련
  const shareFile = async (fileId) => {
    try {
      const response = await fetch(`/api/fileShare`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ fileId, shareExpiration: "ONE_HOUR" }),
      });
      if (!response.ok) throw new Error("파일 공유 실패");
      const result = await response.json();
      const shareUrl = result.data.url;
      alert(`공유 링크: ${shareUrl}\n(클립보드에 복사되었습니다!)`);
      navigator.clipboard.writeText(shareUrl);
    } catch (error) {
      console.error(error);
      alert("파일 공유 중 오류가 발생했습니다.");
    }
  };

  const fetchSharedFiles = async () => {
    try {
      const response = await fetch(`/api/fileShare`);
      if (!response.ok) throw new Error("공유 파일 목록 조회 실패");
      const result = await response.json();
      setFiles(result.data.fileShares.map(share => ({
        name: share.fileName,
        type: "file",
        size: share.size,
        shared: true,
        id: share.fileShareId,
      })));
    } catch (error) {
      console.error(error);
      alert("공유 파일 목록 조회 중 오류가 발생했습니다.");
    }
  };

  const deleteSharedFile = async (fileShareId) => {
    try {
      const response = await fetch(`/api/fileShare/${fileShareId}`, {
        method: "DELETE",
      });
      if (!response.ok) throw new Error("공유 파일 삭제 실패");
      alert("공유 파일이 삭제되었습니다.");
      fetchSharedFiles();
    } catch (error) {
      console.error(error);
      alert("공유 파일 삭제 중 오류가 발생했습니다.");
    }
  };

  // 리소스 이동 (PATCH /api/resources/move)
  const moveResource = async (resourceId, targetResourceId, resourceType) => {
    try {
      const response = await fetch("/api/resources/move", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ resourceId, targetResourceId, resourceType }),
      });
      if (!response.ok) throw new Error("리소스 이동 실패");
      alert("리소스가 성공적으로 이동되었습니다.");
      fetchFiles(currentFolderId);
    } catch (error) {
      console.error(error);
      alert("리소스 이동 중 오류가 발생했습니다.");
    }
  };

  const handleMoveResource = async () => {
    if (!selectedResource || !targetFolderId) {
      alert("이동할 대상 및 경로를 선택하세요.");
      return;
    }
    try {
     // 폴더일 때: "FOLDER", 파일일 때: "FILE"
     const upperType = selectedResource.type === "folder" ? "FOLDER" : "FILE";
  
      await moveResource(
        selectedResource.id,
        Number(targetFolderId),
       selectedResource.type,
        upperType
      );
      alert(`${selectedResource.name}가 이동되었습니다.`);
      fetchFiles(currentFolderId);
      setMovePopupOpen(false);
      setTargetFolderId("");
    } catch (error) {
      console.error("리소스 이동 오류:", error);
    }
  };
  

  // 검색 핸들러
  const handleSearchChange = (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    if (!query.trim()) {
      fetchFiles(currentFolderId);
    } else {
      searchFiles(query.trim());
    }
  };

  // 정렬 핸들러
  const handleSort = (key) => {
    if (sortKey === key) {
      setSortOrder(prev => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortKey(key);
      setSortOrder("asc");
    }
  };

  // 정렬된 파일
  const sortedFiles = [...files].sort((a, b) => {
    // 폴더 우선
    if (a.type === "folder" && b.type !== "folder") return -1;
    if (a.type !== "folder" && b.type === "folder") return 1;

    let valueA = a[sortKey];
    let valueB = b[sortKey];

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

  // 메인 렌더
  return (
    <div className="container-wrapper">
      <header>
        {/* 로고 */}
        <button className="logobtn">
          <img src="image/logo.png" alt="logo" className="logoimg" />
        </button>

        {/* 검색 */}
        <div className="searchbar">
          <img src="image/search.png" alt="search" className="searchimg" />
          <input
            className="searchtext"
            placeholder="검색"
            value={searchQuery}
            onChange={handleSearchChange}
          />
        </div>

        {/* 드롭다운 / 사용자 */}
        {isDropdownView && (
          <>
            <button className="text-btn" onClick={() => setSettingPopupOpen(true)}>
              계정관리
            </button>
            <button className="text-btn" onClick={() => navigate("/")}>
              로그아웃
            </button>
          </>
        )}
        <button className="image-btn" onClick={() => setDropdownView(!isDropdownView)}>
          <img
            src={userInfo?.profileImage || "image/user.png"}
            alt="user"
            className="headerimg"
          />
          <h>{userInfo?.name}</h>
        </button>
      </header>

      <main>
        <div className="side-container">
          <button className="side-menu" onClick={() => setFolderPopupOpen(true)}>
            <img src="image/add-folder.png" alt="folder" className="sideimg" />
            새 폴더
          </button>
          <button className="side-menu">
            <img src="image/file-upload.png" alt="file" className="sideimg" />
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
            <button className="storage-item">
              <img src="image/spread.png" alt="spread" className="sideimg" />
              내 저장소
            </button>
          </div>
          <button
              className="side-menu"
              onClick={() => {
                // 공유 파일 목록 조회
                fetchSharedFiles();
              }}
            >
              <img src="image/share.png" alt="share" className="sideimg" />
              공유 파일 목록
            </button>

          <button className="side-menu">
            <img src="image/trash.png" alt="trash" className="sideimg" />
            휴지통
          </button>
          <button className="side-menu" onClick={() => setSettingPopupOpen(true)}>
            <img src="image/setting.png" alt="setting" className="sideimg" />
            설정
          </button>
        </div>

        <div className="main-container">
          <div className="all-file-container">
            <h>{currentFolderId}</h>
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
                {folderHistory.length > 1 && (
                  <tr>
                    <td
                      className="back-btn"
                      colSpan={6}
                      onDoubleClick={handleGoBack}
                    >
                      /..
                    </td>
                  </tr>
                )}
                {sortedFiles.map((file, index) => (
                  <tr
                    key={index}
                    onClick={(e) => {
                      if (e.ctrlKey) {
                        setSelectedFiles((prev) =>
                          prev.includes(file)
                            ? prev.filter((f) => f !== file)
                            : [...prev, file]
                        );
                      } else {
                        setSelectedFiles([file]);
                      }
                    }}
                    onDoubleClick={() => handleDoubleClick(file)}
                    className={selectedFiles.includes(file) ? "selected" : ""}
                  >
                    <td className="file-icon-column">
                      <img
                        src={`image/${file.type === "folder" ? "folder" : "file"}.png`}
                        alt={file.type}
                        className="file-icon"
                      />
                    </td>
                    <td>{file.name}</td>
                    <td>
                      {file.type === "folder" ? "-" : `${(file.size / 1024).toFixed(2)} KB`}
                    </td>
                    <td>{file.type}</td>
                    <td>
                      {file.type === "folder"
                        ? "-"
                        : new Date(file.lastModified).toLocaleString()}
                    </td>
                    <td className="menu-btn-column">
                      {file.type !== "folder" && (
                        <>
                          <button
                            className="menu-btn"
                            onClick={(e) => {
                              e.stopPropagation();
                              downloadFile(file.id, file.name);
                            }}
                          >
                            <img src="image/download.png" alt="Download" />
                          </button>
                          <button
                            className="menu-btn"
                            onClick={(e) => {
                              e.stopPropagation();
                              shareFile(file.id);
                            }}
                          >
                            <img src="image/share.png" alt="Share" />
                          </button>
                        </>
                      )}
                      <button
                        className="menu-btn"
                        onClick={(e) => {
                          e.stopPropagation();
                          if (window.confirm(`정말로 ${file.name}를 삭제하시겠습니까?`)) {
                            file.type === "folder"
                              ? deleteFolder(file.id)
                              : deleteFile(file.id);
                          }
                        }}
                      >
                        <img src="image/delete.png" alt="Delete" />
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
              <button onClick={handleCreateFolder} className="popup-btn">확인</button>
              <button onClick={() => setFolderPopupOpen(false)} className="popup-btn">취소</button>
            </div>
          </div>
        </div>
      )}

      {/* 이동 팝업 */}
      {isMovePopupOpen && (
        <div className="popup-overlay">
          <div className="popup">
            <h2>이동할 폴더 선택</h2>
            <select
              value={targetFolderId}
              onChange={(e) => setTargetFolderId(e.target.value)}
              className="popup-select"
            >
              <option value="">폴더 선택</option>
              {folders.map((folder) => (
                <option key={folder.id} value={folder.id}>
                  {folder.name}
                </option>
              ))}
            </select>
            <div className="popup-buttons">
              <button onClick={handleMoveResource} className="popup-btn">확인</button>
              <button onClick={() => setMovePopupOpen(false)} className="popup-btn">취소</button>
            </div>
          </div>
        </div>
      )}

      {/* 설정 팝업 */}
      {isSettingPopupOpen && (
        <div className="popup-overlay">
          <div className="popup-setting">
            <div className="setting-menu">
              <button>계정설정</button>
              <button>환경설정</button>
            </div>
            <div className="setting-main">
              <h>설정</h>
              <button onClick={() => setSettingPopupOpen(false)} className="popup-btn">
                닫기
              </button>
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
    </div>
  );
}

export default MainPage;
 