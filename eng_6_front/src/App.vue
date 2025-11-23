<script setup></script>

<template>
  <div v-loading="top_loading">
    <div style="display: flex; margin-top: 15px; ">
      <el-image-viewer
          v-if="showPreview"
          show-progress
          :url-list="srcList"
          @close="showPreview = false"
      >
      </el-image-viewer>
      <div style="margin-top: 15px; margin-left: 50px; ">
        <el-input style="width:500px; "
            v-model="globalSearchWordText"
            placeholder="输入单词进行搜索"
            clearable
        />
        <el-button style="margin-left: 10px; " type="text" @click="this.handleGlobalSearch"><span><el-icon size="35"><Search /></el-icon> </span></el-button>
      </div>
    </div>
    <div>
      <h3 style="position: fixed; top: 10px; right: 20px; background: aliceblue; ">已掌握：{{ hasMasteredWords }}，剩余：{{ total }}</h3>
    </div>
    <!-- 嵌入上传弹窗组件 -->
    <ImageUploadModal ref="childRef"
        :visible.sync="showUploadModal"
        @upload="this.handleImageUpload"
    />
    <div style="position: fixed; right: 10px; top: 100px; ">
      <el-button color="darkgreen" plain @click="dialogVisible = true">
        绑定关联词
      </el-button>
      <div style="margin-top: 5px; ">
        <span>当前选中：{{ relSelectedIndex + 1 }}</span>
      </div>
      <div style="margin-top: 5px; cursor: pointer; ">
        背诵模式：
        <el-icon size="20" v-if="reciteMode == false" @click="this.changeState" ><Lock /></el-icon>
        <el-icon size="20" v-if="reciteMode == true" @click="this.changeState" ><Unlock /></el-icon>
      </div>

      <el-dialog
          v-model="dialogAddVisible"
          title="添加例句"
          width="500"
      >
        {{ curr_word }}
        <el-input
            v-model="exampleSent"
            placeholder=""
            clearable
        />
        <div
            v-for="(ex, index) in examples"
            :key="ex.id"
            @selection-change=""
            class="word-item"
        >
            {{ index + 1 + ": " + ex.example }}
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="this.closeAddDialog">关闭</el-button>
            <el-button type="primary" @click="this.submitAddExample">
              提交
            </el-button>
          </div>
        </template>
      </el-dialog>
      <el-dialog
          v-model="dialogEditVisible"
          title="修改释义"
          width="500"
      >
        {{ curr_word }}
        <el-input
            v-model="editMeaningTxt"
            placeholder=""
            clearable
        />
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogEditVisible = false">关闭</el-button>
            <el-button type="primary" @click="this.submitEditMeaning">
              提交
            </el-button>
          </div>
        </template>
      </el-dialog>
      <el-dialog
          v-model="dialogVisible"
          title="关联词"
          width="500"
      >
        <div>
          <div><span>单词A: {{ relSelectedWord }}&nbsp;&nbsp;{{ relSelectedWordMeaning }}</span></div>
          <div style="margin-top: 5px; ">
            <span>单词B: </span>
            <el-input
                v-model="searchWordText"
                placeholder="输入单词前缀进行搜索"
                clearable
            />
            <el-button type="text" @click="this.handleSearch">查找</el-button>
          </div>
        </div>
        <div
            v-for="w in filteredWords"
            :key="w.id"
            @selection-change="handleSelectionChange"
            class="word-item"
            @click="selectWord(word)"
        >

          <div v-if="w.id != relSelectedId">
            <input
                type="checkbox"
                :value="w.id"
                v-model="selectedIds"
            >
            <b>{{ w.word }}</b>&nbsp;&nbsp;&nbsp;
            {{ w.meaning }}
          </div>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
            <el-button type="primary" @click="this.submitRelWord">
              提交
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
    <div>
      <ul style="margin-top: 50px;  ">
        <li v-for="(w, index) in words" :key="w.id">
          <div class="line-item">
            <span class="w-item" style="width: 35px; ">{{ index + 1 }}</span>
            <span><el-button color="lightgreen" @click="this.hasMastered(w.id)"><el-icon><Select /></el-icon></el-button></span>
            <span>
              <el-button color="darkblue" @click="this.notRecognize(w)" style="margin-left: 5px; ">
                <el-icon v-if="w.notRecognize == ''"><CloseBold /></el-icon>
                <el-icon v-else>{{ w.notRecognize }}</el-icon>
              </el-button>

            </span>
            <span>
              <el-button color="lightblue" @click="this.recognize(w)" style="margin-left: 5px; margin-right: 25px; ">
                <el-icon >R</el-icon>
              </el-button>

            </span>
            <span class="w-item">{{ w.word }}</span>
            <span class="w-item">/{{ w.phonetic }}/</span>
            <span class="w-item-meaning" :id="'meaning_' + index" style="cursor: pointer; background-color: #808080; border:  solid 3px #ffffff; color: #808080; " @click="this.show(index, w)">
              释义： {{ w.meaning }}。
              <br/>关联词汇：
              {{ w.relatedWord }}
              <br/>关联例句：
              {{ w.example }}
            </span>
            &nbsp;
            <el-icon :size="20" @click="this.editMeaning(w)" style="cursor: pointer; ">
              <Edit />
            </el-icon>
            <el-icon :size="20" @click="this.addExample(w)" style="cursor: pointer; margin-left: 15px; bottom: 0px; "><Plus /></el-icon>
            <el-icon :size="20" @click="this.showUploadPic(w)" style="cursor: pointer; margin-left: 15px; bottom: 0px; " ><el-icon><Picture /></el-icon></el-icon>
            <el-icon :size="20" v-if="w.hasPic == 1" @click="this.getImageList(w.picList)" style="cursor: pointer; margin-left: 15px; bottom: 0px; color: skyblue; " ><el-icon><Picture /></el-icon></el-icon>
          </div>
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import api from "./api/http"
import ImageUploadModal from './components/imageupload/ImageUploadModal.vue'
import {ElMessage} from "element-plus";

export default {
  components: {
    ImageUploadModal
  },
  data() {
    return {
      top_loading: false,
      words: {},
      selectedIds: [],
      hasMasteredWords: 0,
      total: 0,
      dialogVisible: false,
      relSelectedIndex: '',
      relSelectedId: '',
      relSelectedWord: '',
      relSelectedWordMeaning: '',
      searchWordText: '',
      globalSearchWordText: '',
      filteredWords: [],
      dialogEditVisible: false,
      editMeaningTxt: '',
      editMeaningId: '',
      curr_word: '',
      dialogAddVisible: false,
      examples: [],
      exampleSent: '',
      curr_word_id: '',
      reciteMode: false,
      showUploadModal: false,
      srcList: [],
      showPreview: false
    }
  },
  created() {
    this.fetchWords()
    this.selCount()
  },
  mounted() {
    this.refreshData()
  },
  methods: {
    async handleImageUpload(file) {
      // 在这里处理图片上传逻辑
      console.log('接收到文件:', file)
      const formData = new FormData();
      formData.append('file', file);
      // 添加额外参数
      formData.append("id", this.curr_word_id);
      // 可以调用API上传文件等操作
      await api.post('/engPic/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      }).then(res => {
        console.log("上传完成")
        ElMessage.success('上传成功')
        this.handleGlobalSearch()
      })
    },
    refreshData() {

    },
    async handleGlobalSearch() {
      // 全局搜索
      // alert(this.searchWordText)
      this.top_loading = true
      await api.post('/engCet6/page', {
        word: this.globalSearchWordText
      }).then((response) => {
        console.log(response)
        this.words = response.body.resultData
        this.total = response.body.totalRecord
        this.top_loading = false
      })
    },
    changeState() {
      if (this.reciteMode == false) {
        this.reciteMode = true
        for (let i = 0; i < 1000; i++) {
          document.getElementById("meaning_" + i).style.backgroundColor = "#ffffff";
          document.getElementById("meaning_" + i).style.color = "#000000";
        }
      } else {
        this.reciteMode = false
        for (let i = 0; i < 1000; i++) {
          document.getElementById("meaning_" + i).style.backgroundColor = "#808080";
          document.getElementById("meaning_" + i).style.color = "#808080";
        }
      }

    },
    closeAddDialog() {
      this.fetchWords()
      this.dialogAddVisible = false
    },
    queryExamples(wordId) {
      api.post('/engExaSent/page', {
        wordId: wordId
      }).then((response) => {
            this.examples = response.body.resultData
          }
      )
    },
    submitAddExample() {
      api.post('/engExaSent/save', {
        wordId: this.curr_word_id,
        example: this.exampleSent
      }).then((response) => {
            this.queryExamples(this.curr_word_id)
          }
      )
    },
    async submitEditMeaning() {
      await api.post('/engCet6/save', {
        id: this.editMeaningId,
        meaning: this.editMeaningTxt
      }).then((response) => {
            if (response.code == 200) {
              // alert("")
              this.fetchWords()
              this.dialogEditVisible = false
            }
          }
      )
    },
    getImageList(list) {
      this.srcList = []
      list.forEach(l => {
        this.srcList.push("http://localhost:8098/engPic/image/" + l)
      })
      console.log(this.srcList)
      this.showPreview = true
    },
    showUploadPic(w) {
      this.$refs.childRef.showModal = true
      this.showUploadModal = true
      this.curr_word_id = w.id
    },
    addExample(w) {
      this.dialogAddVisible = true
      this.curr_word_id = w.id
      this.curr_word = w.word
      this.queryExamples(w.id)
    },
    editMeaning(w) {
      this.editMeaningTxt = w.meaning
      this.editMeaningId = w.id
      this.curr_word = w.word
      this.dialogEditVisible = true
    },
    async submitRelWord() {
      // this.dialogVisible = false
      await api.post('/engWordRel/save', {
        mainId: this.relSelectedId,
        ids: this.selectedIds
      }).then((response) => {
            if (response.code == 200) {
              // alert("绑定成功")
              this.fetchWords()
            }
          }
      )
    },
    handleSelectionChange() {

    },
    async handleSearch() {
      // alert(this.searchWordText)
      this.selectedIds = []
      await api.post('/engCet6/pageFirst10', {
        word: this.searchWordText
      }).then((response) => {
        console.log(response)
        this.filteredWords = response.body.resultData
      })
    },
    relWord(id) {

    },
    show(index, w) {
      document.getElementById("meaning_" + index).style.backgroundColor = "#ffffff"
      document.getElementById("meaning_" + index).style.color = "#000000"
      this.relSelectedIndex = index
      this.relSelectedId = w.id
      this.relSelectedWord = w.word
      this.relSelectedWordMeaning = w.meaning
      for (let i = 0; i < 1000; i++) {
        if (i == index) {
          document.getElementById("meaning_" + i).style.border = "dotted 3px darkgreen"
        } else {
          document.getElementById("meaning_" + i).style.border = "solid 3px #ffffff"
        }
      }

    },
    async recognize(w) {
      let notRec = w.notRecognize
      if (notRec == undefined) {
        notRec = 0
      } else {
        notRec = notRec - 1
      }
      await api.post('/engCet6/save', {
        id: w.id,
        notRecognize: notRec
      }).then((response)=> {
        this.handleGlobalSearch()
      })
    },
    async notRecognize(w) {
      let notRec = w.notRecognize
      if (notRec == undefined) {
        notRec = 1
      } else {
        notRec = notRec + 1
      }
      await api.post('/engCet6/save', {
        id: w.id,
        notRecognize: notRec
      }).then((response)=> {
        this.handleGlobalSearch()
      })
    },
    async hasMastered(id) {
      await api.post('/engCet6/save', {
        id: id,
        hasMastered: 1
      })
      await this.fetchWords(), await this.selCount()
    },
    async selCount() {
      const response = await api.post('/engCet6/selCount', {
        hasMastered: 1
      })
      console.log(response)
      this.hasMasteredWords = response.body
    },
    // 获取用户列表
    async fetchWords() {
      await this.handleGlobalSearch()
      // this.top_loading = true
      // const response = await api.post('/engCet6/page', {}).then((response)=> {
      //   console.log(response)
      //   this.words = response.body.resultData
      //   this.total = response.body.totalRecord
      //
      //   this.top_loading = false
      // })
    },
    getErrorMessage(error) {
      if (error.response?.data?.message) {
        return error.response.data.message
      }
      return error.message || '请求失败，请检查网络连接'
    }
  }
}
</script>

<style scoped>
.line-item {
  padding: 5px 0 5px 0;
}

.w-item-index {
  display: inline-block;
  width: 100px;
}

.w-item {
  display: inline-block;
  width: 200px;
}

.w-item-meaning {
  display: inline-block;
  width: 400px;
}

</style>
