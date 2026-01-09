<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script type="text/javascript"
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style type="text/css">
.joinCSS {
	margin: 0px auto;
	width: 900px;
}
</style>
</head>
<body>
	<!-- ****** Breadcumb Area Start ****** -->
	<div class="breadcumb-area"
		style="background-image: url(/img/bg-img/breadcumb.jpg);">
		<div class="container h-100">
			<div class="row h-100 align-items-center">
				<div class="col-12">
					<div class="bradcumb-title text-center">
						<h2>회원가입</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="breadcumb-nav">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav aria-label="breadcrumb">
						<ol class="breadcrumb">
							<%-- 검색기 --%>
						</ol>
					</nav>
				</div>
			</div>
		</div>
	</div>
	<!-- ****** Breadcumb Area End ****** -->

	<!-- ****** Archive Area Start ****** -->
	<section class="archive-area section_padding_80" id="join_section">
		<div class="container" style="max-width: 800px; margin-top: 40px;">

			<div class="panel panel-pastel">
				<div class="panel-heading text-center">
					<span class="glyphicon glyphicon-leaf"></span>
				</div>
				<div class="panel-body">
					<form id="frm" ref="frm" method="post" action="/member/join_ok">
						<table class="table table-bordered table-hover">
							<tr>
								<th class="text-center" width="20%">ID</th>
								<td>
									<div class="form-inline">
										<input type="text" name="userid" ref="userid"
											class="form-control input-sm" placeholder="아이디"
											v-model="userid" v-bind:readonly="isReadOnly">
										<button type="button" class="btn btn-mint btn-sm"
											@click="idCheck()">중복체크</button>
										&nbsp;<span style="color: red; font-size: 12px;">{{idOk}}</span>
									</div>
								</td>
							</tr>

							<tr>
								<th class="text-center">Password</th>
								<td>
									<div class="form-inline">
										<input type="password" v-model="userpwd" name="userpwd"
											ref="userpwd" class="form-control input-sm"
											placeholder="비밀번호"> <input type="password"
											v-model="userpwd1" name="userpwd1" ref="userpwd1"
											class="form-control input-sm" placeholder="비밀번호 재입력">
									</div>
								</td>
							</tr>

							<tr>
								<th class="text-center">이름</th>
								<td><input type="text" name="username" ref="username"
									v-model="username" class="form-control input-sm"
									placeholder="이름 입력"></td>
							</tr>

							<tr>
								<th class="text-center">성별</th>
								<td><label class="radio-inline"><input type="radio"
										name="sex" value="남자" checked> 남자</label> <label
									class="radio-inline"><input type="radio" name="sex"
										value="여자"> 여자</label></td>
							</tr>

							<tr>
								<th class="text-center">이메일</th>
								<td><input type="text" name="email" ref="email"
									v-model="email" class="form-control input-sm"
									placeholder="example@email.com"></td>
							</tr>

							<tr>
								<th class="text-center">우편번호</th>
								<td>
									<div class="form-inline">
										<input type="text" ref="post" name="post" v-model="post"
											class="form-control input-sm" readonly>
										<button type="button" @click="postFind()"
											class="btn btn-pink btn-sm">우편번호검색</button>
									</div>
								</td>
							</tr>

							<tr>
								<th class="text-center">주소</th>
								<td><input type="text" ref="addr1" name="addr1"
									v-model="addr1" class="form-control input-sm" readonly></td>
							</tr>

							<tr>
								<th class="text-center">상세주소</th>
								<td><input type="text" name="addr2" v-model="addr2"
									class="form-control input-sm"></td>
							</tr>

							<tr>
								<th class="text-center">전화번호</th>
								<td>
									<div class="form-inline">
									   <input type="text" name="phone" v-model="phone"
											class="form-control input-sm" placeholder="####-####">
									</div>
								</td>
							</tr>

							<tr>
								<td colspan="2" class="text-center">
									<button type="button" class="btn btn-mint btn-sm"
										@click="join()">회원가입</button>
									<button type="button" class="btn btn-pink btn-sm"
										onclick="history.back()">취소</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>

	</section>
	<script>
      let joinApp=Vue.createApp({
         data(){
            return {
               userid:'',
               username:'',
               userpwd:'',
               userpwd1:'',
               sex:'',
               email:'',
               post:'',
               addr1:'',
               addr2:'',
               
               isReadOnly:false,
               idOk:'',
            }
         },
         methods:{
            // idCheck
            idCheck(){
               if(this.userid==='')
               {
                  this.$refs.userid.focus()
                  return
               }
               
               axios.get("/member/idCheck_vue/",{
            	   params: {
                     userid:this.userid
            	   }
               }).then(response=>{
                  console.log(response.data)
                  if(response.data===0)
                  {
                     this.idOk='사용 가능한 아이디입니다'
                     this.isReadOnly=true
                  }
                  else
                  {
                     this.idOk='이미 사용중인 아이디입니다'
                     this.userid=''
                     this.$refs.userid.focus()
                  }
               }).catch(error=>{
                  console.log(error.response)
               })
            },
            postFind(){
               let _this=this
               new daum.Postcode({
                  oncomplete:function(data)
                  {
                     _this.post=data.zonecode
                     _this.addr1=data.address
                  }
               }).open()
            },
            join(){
               if(this.userid==='')
               {
                  this.$refs.userid.focus()
                  return
               }
               if(this.userpwd==='')
               {
                  this.$refs.userpwd.focus()
                  return
               }
               if(this.userpwd1==='')
               {
                  this.$refs.userpwd1.focus()
                  return
               }
               if(this.userpwd!==this.userpwd1)
               {
                  this.userpwd=''
                  this.userpwd1=''
                  this.$refs.userpwd.focus()
                  return
               }
               if(this.username==='')
               {
                  this.$refs.username.focus()
                  return
               }
               if(this.birthday==='')
               {
                  this.$refs.birthday.focus()
                  return
               }
               if(this.email==='')
               {
                  this.$refs.email.focus()
                  return
               }
               
               this.$refs.frm.submit()
            }
            // join
         }
      })
      joinApp.mount('#join_section')
   </script>
</body>
</html>