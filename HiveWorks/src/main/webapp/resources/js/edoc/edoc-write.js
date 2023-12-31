/**
 * 전자문서 기안페이지에서 사용하는 JS 모음
 * 작성자 : 이재연
 */

/*
 * CKEditor에서 사용하는 이미지 업로드용 어댑터
 */
class ElectronicDocumentImageUploadAdapter{
	constructor(loader){
		this.loader = loader;
	}
	// Starts the upload process.
    upload() {
        return this.loader.file
            .then( file => new Promise( ( resolve, reject ) => {
                this._initRequest();
                this._initListeners( resolve, reject, file );
                this._sendRequest( file );
            } ) );
    }

    // Aborts the upload process.
    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    // Initializes the XMLHttpRequest object using the URL passed to the constructor.
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();

        // Note that your request may look different. It is up to you and your editor
        // integration to choose the right communication channel. This example uses
        // a POST request with JSON as a data structure but your configuration
        // could be different.
        xhr.open( 'POST', path+'/edoc/imgupload', true );
        xhr.responseType = 'json';
    }

    // Initializes XMLHttpRequest listeners.
    _initListeners( resolve, reject, file ) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = `파일 업로드에 실패했습니다.:${ file.name }`;

        xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        xhr.addEventListener( 'abort', () => reject() );
        xhr.addEventListener( 'load', () => {
            const response = xhr.response;

            // This example assumes the XHR server's "response" object will come with
            // an "error" which has its own "message" that can be passed to reject()
            // in the upload promise.
            //
            // Your integration may handle upload errors in a different way so make sure
            // it is done properly. The reject() function must be called when the upload fails.
            if ( !response || response.error ) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            // If the upload is successful, resolve the upload promise with an object containing
            // at least the "default" URL, pointing to the image on the server.
            // This URL will be used to display the image in the content. Learn more in the
            // UploadAdapter#upload documentation.
            resolve( {
                default: response.url
            } );
        } );

        // Upload progress when it is supported. The file loader has the #uploadTotal and #uploaded
        // properties which are used e.g. to display the upload progress bar in the editor
        // user interface.
        if ( xhr.upload ) {
            xhr.upload.addEventListener( 'progress', evt => {
                if ( evt.lengthComputable ) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            } );
        }
    }

    // Prepares the data and sends the request.
    _sendRequest( file ) {
        // Prepare the form data.
        const data = new FormData();

        data.append( 'upload', file );

        // Important note: This is the right place to implement security mechanisms
        // like authentication and CSRF protection. For instance, you can use
        // XMLHttpRequest.setRequestHeader() to set the request headers containing
        // the CSRF token generated earlier by your application.

        // Send the request.
        this.xhr.send( data );
    }
}

/*
 * 위에 설정한 이미지 업로드용 어댑터를 플러그인 화 하는 함수
 */
function ElectronicDocumentImageUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        // Configure the URL to the upload script in your back-end here!
        return new ElectronicDocumentImageUploadAdapter( loader );
    };
}
/*
 * CK Editer 초기화
 */
DecoupledEditor
.create( document.querySelector("#content"),{
	extraPlugins :[
        // 이미지 업로드용 어댑터 적용
        ElectronicDocumentImageUploadAdapterPlugin
        ]
    })
	.then( editor => {
        const toolbarContainer = document.querySelector( '.editor-toolbar-container' );
        toolbarContainer.appendChild( editor.ui.view.toolbar.element );
    })
    .catch( error => {
        console.error( error );
    });
        
/*
 * 문서종류를 고르면 양식 목록을 가져오는 메소드
 */

 $('#edocType').on('change',(e)=>{
    dotCode = e.target.value;
	const $format = $('#edocFormat');
	fetch(path+"/edoc/formatList?edocDotCode="+e.target.value)
	.then(response=>{
		if(response.status != 200) throw new Error(response.status);
		return response.json();
	})
	.then(result =>{
		$format.html("");
		$format.append($('<option disabled="disabled" selected="selected">').text('서식종류'));
		
		result.forEach((v,i)=>{
			$format.append($('<option>').val(v.sampleNo).text(v.sampleName));
		});
	})
	.catch(e=>{
		alert(e);
		console.log(e);
	});
});

/*
 * 문서 양식을 고르면 본문 내용을 양식 내용으로 치환하는 메소드
 */
let dotCode;
let formatNo;
$('#edocFormat').on('change',(e)=>{
	let processContinue = confirm('작성중인 내용이 전부 사라질 수 있습니다. 진행하시겠습니까?');
	const edocFormatNo = e.target.value;
	if(processContinue){
		fetch(path+"/edoc/formatData?formatNo="+edocFormatNo)
		.then(response=>{
			if(response.status != 200) throw new Error(response.status)
			return response.json();
		})
		.then(data=>{
			console.log(data);
			document.getElementById('content').ckeditorInstance.data.set(data.sampleContent);
            dotCode = data.sampleDotCode;
            formatNo = data.sampleNo;
		})
		.catch(e=>{
				alert(e);
				console.log(e);
			})
	}
});





function getDeptList(){
	let rootDeptCode;
	fetch(path+'/deptlist')
	.then(response=>{
		if(response.status != 200) throw new Error(response.status)
		return response.json();
	})
	.then(data=>{
		var deptlist = new Array();
		$.each(data, function(idx, item){
			deptlist[idx]={id:item.deptCode, parent:item.deptUpstair, text:item.deptName};
			if(idx == 0) rootDeptCode = item.deptCode;
		});
		
		$('#deptTree').jstree({
			'plugins':['types','sort','search'],
			'core':{
				'data':deptlist,
				'check_callback': true
			},
			'types':{
				'default':{
					'icon':'fa-solid fa-book-open-reader'
				}
			}
		}).bind("select_node.jstree", function (e, data) {
			let nodeId = data.node.id;
			loadDeptEmpList(nodeId);
		}).on('ready.jstree', function() {
			    $(this).jstree('open_all');	//항상 노드 전체 오픈
				loadDeptEmpList(rootDeptCode);
		});
	})
	.catch(e=>{ alert("조직도 구성에 실패하였습니다. 관리자에게 문의하세요"); console.log(e);
	});
}
//선택된 부서의 구성원 목록을 가져오는 ajax함수
function loadDeptEmpList(nodeId) {
	fetch(path+'/edoc/approvalList?deptCode='+nodeId)
	.then(response=>{
		if(response.status != 200) throw new Error(response.status)
		return response.json();
	})
	.then(data=>{
		let $employeeList = $('#employee-list');
		// 선택목록 내용 비우기
		$employeeList.empty();
		// 선택한 부서 사람으로 선택목록 채우기
		data.forEach((v)=>{
			const $option = $('<option>').val(v.empNo).text(v.name+' ('+v.dept+':'+v.position+')');
			$employeeList.append($option);
		});
	})
	.catch(e=>{
		alert("구성원 정보 로딩 실패. 관리자에게 문의하세요");
	});
}

// 부서원 전체 선택 버튼
$('#employeeSelectAllBtn').on('click',()=>{
    $('#employee-list').val($('#employee-list').find('option').map(function(){return $(this).val()}).get());
});

// 부서원 전체 선택 해제 버튼
$('#employeeDeselectAllBtn').on('click',()=>{
    $('#employee-list').val('');
})

const approvalList = [];
const referenceList = [];

const isExistInLists = (empNo)=>{
	return approvalList.some((e)=>e.empNo == empNo)||referenceList.some((e)=>e.empNo == empNo);
}
$('#addApprovalList').on('click',(e)=>{});
$('#removeApprovalList').on('click',(e)=>{});
$('#addReferenceList').on('click',(e)=>{});
$('#removeReferenceList').on('click',(e)=>{});


/*
 * 기안하기 버튼을 눌렀을 때
 */

$('#submitButton').on('click',()=>{
    // form validate

    // 전자문서 객체 생성
	const edoc  = {
				edocTitle : $('#edocTitle').val(),
				edocDotCode : dotCode,
				edocDsgCode : $('#edocDsgCode').val(),
				creater : $('#edocCreter').val(),
				period : $('#period').val(),
				edocContent : $('#content').html()
			};
    // fetch로 전송
	fetch(path+'/edoc/write',{
		method : 'post',
		headers: {
      		"Content-Type": "application/json",
    	},
    	body : JSON.stringify(edoc)
	})
	.then(response =>{
		if(response.status != 200) throw new Error(response.status);
		return response.json();
	})
	.then(data=>{
		alert('문서가 정상적으로 기안되었습니다.\n문서번호 : '+data.edocNo);
		location.replace(path+"/edoc/lists/process");
	})
	.catch(e=>{
		alert(e);
		console.log(e);
	})
});


