<template>
  <div>
    <!-- 上传弹窗 -->
    <div class="modal-overlay" v-if="showModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3>上传图片</h3>
          <button class="close-btn" @click="closeModal">&times;</button>
        </div>

        <div class="modal-body">
          <!-- 上传区域 -->
          <div
              class="upload-area"
              @click="triggerFileInput"
              @drop="handleDrop"
              @dragover.prevent
              @dragenter.prevent
          >
            <p>拖放图片到此处或</p>
            <input
                type="file"
                ref="fileInput"
                class="file-input"
                accept="image/*"
                @change="handleFileSelect"
            >
            <button class="browse-btn">选择文件</button>
          </div>

          <!-- 图片预览 -->
          <div class="preview-container">
            <h4 class="preview-title">图片预览</h4>
            <div v-if="previewUrl">
              <img :src="previewUrl" alt="预览" class="preview-image">
            </div>
            <div v-else class="no-preview">
              暂无图片
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button class="cancel-btn" @click="closeModal">取消</button>
          <button class="confirm-btn" @click="uploadImage" :disabled="!selectedFile">确认上传</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ImageUploadModal',
  props: {
    // 父组件可以通过这个prop控制弹窗显示
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      showModal: this.visible,
      selectedFile: null,
      previewUrl: ''
    }
  },
  watch: {
    // 监听visible prop的变化
    visible(newVal) {
      this.showModal = newVal;
    },
    // 监听showModal的变化，通知父组件
    showModal(newVal) {
      console.log("子组件被调用：" + newVal)
      this.$emit('update:visible', newVal);
    }
  },
  methods: {
    // 触发文件选择
    triggerFileInput() {
      this.$refs.fileInput.click();
    },

    // 处理文件选择
    handleFileSelect(event) {
      const file = event.target.files[0];
      if (file && file.type.startsWith('image/')) {
        this.selectedFile = file;
        this.previewUrl = URL.createObjectURL(file);
      }
    },

    // 处理拖放
    handleDrop(event) {
      event.preventDefault();
      const file = event.dataTransfer.files[0];
      if (file && file.type.startsWith('image/')) {
        this.selectedFile = file;
        this.previewUrl = URL.createObjectURL(file);
      }
    },

    // 上传图片
    uploadImage() {
      if (this.selectedFile) {
        // 触发上传事件，将文件传递给父组件
        this.$emit('upload', this.selectedFile);
        // this.closeModal();
      }
    },

    // 关闭弹窗
    closeModal() {
      this.showModal = false;
      this.selectedFile = null;
      this.previewUrl = '';
      // 清除文件输入的值
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = '';
      }
    }
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 4px;
  width: 90%;
  max-width: 400px;
  padding: 0;
}

.modal-header {
  padding: 15px;
  border-bottom: 1px solid #ddd;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.close-btn {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
}

.modal-body {
  padding: 20px;
}

.upload-area {
  border: 2px dashed #ccc;
  border-radius: 4px;
  padding: 30px 20px;
  text-align: center;
  margin-bottom: 15px;
  cursor: pointer;
}

.file-input {
  display: none;
}

.browse-btn {
  padding: 8px 15px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

.preview-container {
  margin-top: 15px;
}

.preview-image {
  max-width: 100%;
  max-height: 200px;
  margin-top: 10px;
}

.no-preview {
  color: #666;
  padding: 20px;
}

.modal-footer {
  padding: 15px;
  border-top: 1px solid #ddd;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.cancel-btn {
  padding: 8px 15px;
  background: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.confirm-btn {
  padding: 8px 15px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.confirm-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}
</style>