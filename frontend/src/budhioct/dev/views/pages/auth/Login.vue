<script setup>

</script>

<template>
  <div class="container">

    <div class="card">

      <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
        <li class="nav-item text-center">
          <a class="nav-link active btl" id="pills-home-tab" data-toggle="pill" href="#pills-home" role="tab" aria-controls="pills-home" aria-selected="true">Login</a>
        </li>
        <li class="nav-item text-center">
          <a class="nav-link btr" id="pills-profile-tab" data-toggle="pill" href="#pills-profile" role="tab" aria-controls="pills-profile" aria-selected="false">Signup</a>
        </li>
      </ul>

      <div class="tab-content" id="pills-tabContent">

        <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
          <div class="form px-4 pt-4">
            <input type="text" class="form-control" placeholder="Email" />
            <input type="password" class="form-control" placeholder="Password" />
            <button class="btn btn-dark btn-block">Login</button>
          </div>
        </div>

        <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">
          <div class="form px-4 pt-4">
            <input type="text" class="form-control" placeholder="Email" />
            <input type="password" class="form-control" placeholder="Password" />
            <select class="custom-select">
              <option selected>Choose...</option>
              <option value="USER">USER</option>
            </select>
            <button class="btn btn-dark btn-block">Signup</button>
          </div>
        </div>

      </div>

    </div>

  </div>
</template>

<style scoped>
.container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.card {
  width: 400px;
  border: 1px solid #0d6efd;
  border-radius: 10px;
  background: white;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  padding: 20px 0;
}

/* Style tombol navigasi */
.nav-pills {
  display: flex;
  justify-content: center;
  padding: 0 10px;
}

.nav-item {
  flex: 1;
}

.nav-link {
  border-radius: 0;
  border-bottom: 2px solid #0d6efd40;
}

.btr {
  border-top-right-radius: 5px !important;
}

.btl {
  border-top-left-radius: 5px !important;
}

/* Style form */
.form {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding-bottom: 20px;
}

.form input,
.form select {
  border-radius: 5px;
  padding: 10px;
}

.form button {
  margin-top: 10px;
}

.btn-dark {
  background-color: #0d6efd;
  border-color: #0d6efd;
  color: white;
  padding: 10px;
  border-radius: 5px;
}

.btn-dark:hover {
  background-color: #0b5ed7;
}
</style>