<script>
export default {
  name: 'Register',
  data() {
    return {
      checked: true,
      loginForm: {
        username: '',
        password: ''
      },
      loading: false
    }
  },
  methods: {
    register() {
      // console.log('注册事件开始···')
      if (this.loginForm.username === '') {
        this.$alert('用户名不能为空！', '提示', {
          confirmButtonText: '确定'
        })
      } else if (this.loginForm.password === '') {
        this.$alert('密码不能为空！！', '提示', {
          confirmButtonText: '确定'
        })
      } else {
        var _this = this
        this.$axios
          .post('/register', {
            username: this.loginForm.username,
            password: this.loginForm.password
          }).then(resp => {
          if (resp && resp.status === 200 && resp.data.code === 200) {
            console.log(resp)
            this.$alert('注册成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$router.replace('/login')
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        }).catch(failResponse => {
        })
      }
    }
  }
}
</script>

<template>
  <body id="paper">
  <el-form class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">用户注册</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 40%;background: #505458;border: none" v-on:click="register">注册
      </el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<style>
#paper {
  background: url("../assets/eva.png") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

body {
  margin: -5px 0;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
