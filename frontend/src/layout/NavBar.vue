<template>
    <Disclosure as="nav" class="bg-D5E9B3">
    <div class="mx-auto max-w-8xl px-2 sm:px-6 lg:px-8">
      <div class="relative flex h-24 items-center justify-between">
        <div class="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
            <a class="flex flex-shrink-0 items-center" href="/">
              <img class="h-12 w-auto" src="@/assets/img/logo-yeoro.png" alt="logo"/>
            </a>
        </div>
        <div class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
  
            <!-- Profile dropdown -->
            <Menu as="div" class="relative ml-3">
            <div>
                <MenuButton class="relative flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
                <span class="absolute -inset-1.5" ></span>
                <span class="sr-only">Open user menu</span>
                <img class="h-8 w-8 rounded-full" src="@/assets/img/logo-yeoro.png" alt="" />
                </MenuButton>
            </div>
            <transition enter-active-class="transition ease-out duration-100" enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100" leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
                <MenuItems class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                <MenuItem v-slot="{ active }">
                    <a href="#" :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">내 정보</a>
                </MenuItem>
                <MenuItem v-slot="{ active }">
                    <a href="#" :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">로그아웃</a>
                </MenuItem>
                <MenuItem v-slot="{ active }">
                    <a href="#" :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">고객센터</a>
                </MenuItem>
                </MenuItems>
            </transition>
            </Menu>
        </div>
      </div>
    </div>
  
  
    </Disclosure>
  </template>
  
  <script setup>
  import { Disclosure, Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
  
  </script>
  