<script>
export default {
  name: 'UserProfile',
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码！'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码！'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入的密码不一致！'))
      } else {
        callback()
      }
    }
    return {
      users: [],
      roles: [],
      dialogFormVisible: false,
      selectedUser: [],
      resetFormVisible: false,
      ruleForm: {
        pass: '',
        checkPass: ''
      },
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ]
      }
    }
  },
  mounted() {
    this.listUser()
    this.listRoles()
  },
  methods: {
    listUser() {
      var _this = this
      this.$axios.get('/admin/user').then(resp => {
        console.log(resp)
        if (resp && resp.status === 200) {
          _this.users = resp.data
        }
      })
    },
    listRoles() {
    },
    editUser(user) {
      console.log('editUser')
      this.dialogFormVisible = true
      this.selectedUser = user
    },
    resetPassword(username) {
      console.log(username)
      this.resetFormVisible = true
    },
    onSubmit(user) {
      // let _this = this
      this.$axios.put('/admin/user', {
        username: user.username,
        name: user.name,
        phone: user.phone,
        email: user.email
      }).then(resp => {
        if (resp && resp.status === 200) {
          this.$alert('用户信息修改成功！')
          this.dialogFormVisible = false
          this.listUser()
        } else {
          this.$alert(resp.data)
        }
      })
    },
    commitStatusChange(value, user) {
      if (user.username !== 'admin') {
        console.log('更改用户状态！')
        this.$axios.put('/admin/user/status', {
          // admin/user/status
          enabled: value,
          username: user.username
        }).then(resp => {
          if (resp && resp.data.code === 200) {
            if (value) {
              this.$message('用户 [' + user.username + '] 已启用')
            } else {
              this.$message('用户 [' + user.username + '] 已禁用')
            }
          }
        }).catch(failure => {
          this.$message(failure)
          user.enabled = !value
        })
      } else {
        user.enabled = true
        this.$alert('不能禁用管理员账户')
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('submit:' + valid)
          console.log(this.ruleForm)
          const _this = this
          this.$axios.put('/admin/user/resetPassword', {
            password: _this.ruleForm.checkPass,
            username: _this.selectedUser.username
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$alert('success')
              _this.resetForm(formName)
              _this.resetFormVisible = false
            } else {
              _this.$alert('failure' + resp)
            }
          }).catch(e => {
            _this.$alert('failure' + e)
          })
        } else {
          console.log('ERROR submit:' + valid)
          return false
        }
      })
    },
    resetForm(formName) {
      console.log('reset...')
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<template>
  <div>
    <el-dialog
      title="重置密码"
      :visible.sync="resetFormVisible">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <!--      <el-form-item label="年龄" prop="age">-->
        <!--        <el-input v-model.number="ruleForm.age"></el-input>-->
        <!--      </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">清空</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
      title="修改用户信息"
      :visible.sync="dialogFormVisible">
      <el-form v-model="selectedUser" style="text-align: left" ref="dataForm">
        <el-form-item label="用户名" label-width="120px" prop="username">
          <label>{{ selectedUser.username }}</label>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="120px" prop="name">
          <el-input v-model="selectedUser.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" prop="phone">
          <el-input v-model="selectedUser.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="120px" prop="email">
          <el-input v-model="selectedUser.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="120px" prop="password">
          <el-button type="warning" @click="resetPassword(selectedUser.username)">重置密码</el-button>
        </el-form-item>
        <!--        <el-form-item label="角色分配" label-width="120px" prop="roles">-->
        <!--          <el-checkbox-group v-model="selectedRolesIds">-->
        <!--            <el-checkbox v-for="(role,i) in roles" :key="i" :label="role.id">{{ role.nameZh }}</el-checkbox>-->
        <!--          </el-checkbox-group>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit(selectedUser)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
      :data="users"
      stripe>
      <el-table-column
        prop="id"
        label="id"
        width="100">
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        fit>
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称"
        fit>
      </el-table-column>
      <el-table-column
        prop="phone"
        label="电话"
        sortable
        fit>
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
        sortable
        fit>
      </el-table-column>
      <el-table-column
        prop="enabled"
        label="状态"
        width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="(value) => commitStatusChange(value,scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="120">
        <template slot-scope="scope">
          <el-button
            @click="editUser(scope.row)"
            type="text"
            size="small">
            编辑
          </el-button>
          <!--          <el-button-->
          <!--            type="text"-->
          <!--            size="small">-->
          <!--            移除-->
          <!--          </el-button>-->
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>

</style>
