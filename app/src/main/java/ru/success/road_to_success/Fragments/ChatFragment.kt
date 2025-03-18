package ru.success.road_to_success.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.success.road_to_success.AdaptersAndClasses.Chat.ChatAdapter
import ru.success.road_to_success.AdaptersAndClasses.Chat.ChatItems
import ru.success.road_to_success.DTO.Chat.ChatConversationAllData
import ru.success.road_to_success.DTO.Chat.ChatConversationAllDataInterface
import ru.success.road_to_success.DTO.Chat.ChatId
import ru.success.road_to_success.DTO.Chat.ChatIdInterface
import ru.success.road_to_success.DTO.Chat.ChatItemsAllData
import ru.success.road_to_success.DTO.Chat.ChatItemsAllDataInterface
import ru.success.road_to_success.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var toggleButtons: List<ToggleButton>
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private var idList: List<String> = mutableListOf()
    private var idListConvo: List<String> = mutableListOf()
    private var chatItemsList: MutableList<ChatItems> = mutableListOf()
    private lateinit var chatAdapter: ChatAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        loadCredentials()
        fetchId()




        toggleButtons = listOf(
            binding.chatButton1,
            binding.chatButton2,
            binding.chatButton3,
            binding.chatButton4,
            binding.chatButton5
        )

        toggleButtons.forEach { button ->
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherButtons(button)
                }
            }
        }

    }




    private fun fetchId() {
        val retrofit = createRetrofit()
        val chatIdService = retrofit.create(ChatIdInterface::class.java)

        chatIdService.getChatId(ACCESS_TOKEN).enqueue(object : Callback<ChatId> {
            override fun onResponse(call: Call<ChatId>, response: Response<ChatId>) {
                if (response.isSuccessful) {
                    val chatAlbumItems = response.body()?.response?.items ?: emptyList()

                    idList = chatAlbumItems
                        .map { chatItem -> chatItem.conversation.peer.id }
                        .filterNot { id -> id.startsWith("-") || id.startsWith("200000") }

                    idListConvo = chatAlbumItems
                        .map { chatItem -> chatItem.conversation.peer.id }
                        .filter { id -> id.startsWith("200000") }

                    fetchProfileForEachId()
                    fetchProfileForEachIdConvo()


                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatId>, t: Throwable) {
                Log.e("API_ERROR", "Failure Call", t)
            }
        })
    }

    private fun fetchProfileForEachId() {
        val retrofit = createRetrofit()
        val chatAllDataService = retrofit.create(ChatItemsAllDataInterface::class.java)

        idList.forEach { id ->
            chatAllDataService.getChatItemsAllData(id, ACCESS_TOKEN).enqueue(object : Callback<ChatItemsAllData> {
                override fun onResponse(call: Call<ChatItemsAllData>, response: Response<ChatItemsAllData>) {
                    if (response.isSuccessful) {
                        val profiles = response.body()?.response?.profiles ?: emptyList()
                        val items = response.body()?.response?.items ?: emptyList()
                        if (profiles.isNotEmpty()) {
                            val profile = profiles[0]
                            val profilePhotoUrl = profile.photo100
                            val profileName = "${profile.firstname} ${profile.lastname}"

                            val cmid = if (items.isNotEmpty()) items[0].lastconversationmessageid else ""

                            chatItemsList.add(
                                ChatItems(
                                    profilePhotoUrl = profilePhotoUrl,
                                    profileName = profileName,
                                    cmid = "CMID: $cmid",
                                    id = "ID: $id"
                                )
                            )

                            chatAdapter = ChatAdapter(chatItemsList)
                            binding.recyclerViewChat.adapter = chatAdapter

                        } else {
                            Log.e("API_ERROR", "No profiles found for ID: $id")
                        }
                    } else {
                        Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ChatItemsAllData>, t: Throwable) {
                    Log.e("API_ERROR", "Failure Call", t)
                }
            })
        }
    }


    private fun fetchProfileForEachIdConvo() {
        val retrofit = createRetrofit()
        val chatAllDataServiceConvo = retrofit.create(ChatConversationAllDataInterface::class.java)

        idListConvo.forEach { id ->
            chatAllDataServiceConvo.getChatConversationAllData(id, ACCESS_TOKEN).enqueue(object : Callback<ChatConversationAllData> {
                override fun onResponse(call: Call<ChatConversationAllData>, response: Response<ChatConversationAllData>) {
                    if (response.isSuccessful) {
                        val items = response.body()?.response?.items ?: emptyList()

                        val chatConvoItems = items.map { chatItem ->
                            val profilePhotoUrl = chatItem.chatsettings.photo?.photo100
                            val finalPhotoUrl = if (profilePhotoUrl.isNullOrEmpty()) {
                                "https://vk.com/images/camera_100.png"
                            } else {
                                profilePhotoUrl
                            }

                            ChatItems(
                                profilePhotoUrl = finalPhotoUrl, // Устанавливаем окончательный URL
                                profileName = chatItem.chatsettings.title, // Название чата
                                id = "ID: ${chatItem.peer.id}", // ID чата
                                cmid = "CMID: ${chatItem.lastconversationmessageid}" // Последний cmid
                            )
                        }
                        chatItemsList.addAll(chatConvoItems)

                        chatAdapter = ChatAdapter(chatItemsList)
                        binding.recyclerViewChat.adapter = chatAdapter

                    } else {
                        Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ChatConversationAllData>, t: Throwable) {
                    Log.e("API_ERROR", "Failure Call", t)
                }
            })
        }
    }


    private fun uncheckOtherButtons(checkedButton: ToggleButton) {
        toggleButtons.forEach { button ->
            if (button != checkedButton) {
                button.isChecked = false
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun loadCredentials() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        ACCESS_TOKEN = sharedPreferences.getString("access_token", "0").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.vk.com/method/")
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var ACCESS_TOKEN = ""
        var ID = ""
        var CMID = ""

        fun newInstance() = ChatFragment()
    }

}